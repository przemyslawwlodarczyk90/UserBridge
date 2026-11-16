/**
 * Minimal fetch wrapper for the UserBridge API.
 *  - prefixes every path with VITE_API_URL (empty = same origin / Vite proxy)
 *  - attaches the JWT as "Authorization: Bearer <token>"
 *  - parses JSON or plain-text responses (the backend returns both)
 *  - turns every non-2xx response into an ApiError with a readable message
 */
const BASE_URL = (import.meta.env.VITE_API_URL || '').replace(/\/$/, '');
const TOKEN_KEY = 'userbridge.token';

export const tokenStorage = {
  get: () => localStorage.getItem(TOKEN_KEY),
  set: (token) => localStorage.setItem(TOKEN_KEY, token),
  clear: () => localStorage.removeItem(TOKEN_KEY),
};

export class ApiError extends Error {
  constructor(status, message, fieldErrors = null) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.fieldErrors = fieldErrors; // { fieldName: "message" } for validation errors
  }
}

// Called on 401 for authenticated requests - AuthContext plugs logout in here.
let onUnauthorized = () => {};
export const setUnauthorizedHandler = (handler) => {
  onUnauthorized = handler;
};

async function parseBody(response) {
  if (response.status === 204) return null;
  const text = await response.text();
  if (!text) return null;
  const type = response.headers.get('content-type') || '';
  if (type.includes('application/json')) {
    try {
      return JSON.parse(text);
    } catch {
      return text;
    }
  }
  return text;
}

function toApiError(status, body) {
  if (body && typeof body === 'object') {
    // Spring default error body: { status, error, message, path }
    if (body.message || body.error) {
      return new ApiError(status, body.message || body.error);
    }
    // Validation errors from GlobalExceptionHandler: { field: message }
    const messages = Object.values(body);
    return new ApiError(status, messages[0] || 'Validation failed', body);
  }
  const fallback = {
    401: 'You are not logged in or your session has expired.',
    403: 'You do not have permission to do this.',
    404: 'Resource not found.',
  };
  return new ApiError(status, body || fallback[status] || `Request failed (${status})`);
}

export async function request(path, { method = 'GET', body, auth = true } = {}) {
  const headers = {};
  if (body !== undefined) headers['Content-Type'] = 'application/json';

  const token = tokenStorage.get();
  if (auth && token) headers.Authorization = `Bearer ${token}`;

  let response;
  try {
    response = await fetch(`${BASE_URL}${path}`, {
      method,
      headers,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });
  } catch {
    throw new ApiError(0, 'Cannot reach the server. Is the backend running?');
  }

  const data = await parseBody(response);
  if (!response.ok) {
    if (response.status === 401 && auth && token) onUnauthorized();
    throw toApiError(response.status, data);
  }
  return data;
}
