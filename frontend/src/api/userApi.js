import { request } from './client.js';

/**
 * One function per backend endpoint - the only place that knows the URLs.
 * Add new endpoints of your next app here (or in a sibling file).
 */
export const userApi = {
  register: ({ password, ...userDto }) =>
    request('/api/users/register', { method: 'POST', body: { userDto, password }, auth: false }),

  login: (email, password) =>
    request('/api/users/login', { method: 'POST', body: { email, password }, auth: false }),

  confirm: (token) =>
    request(`/api/users/confirm?token=${encodeURIComponent(token)}`, { auth: false }),

  forgotPassword: (email) =>
    request('/api/users/password/forgot', { method: 'POST', body: { email }, auth: false }),

  resetPassword: (token, newPassword) =>
    request('/api/users/password/reset', { method: 'POST', body: { token, newPassword }, auth: false }),

  me: () => request('/api/users/me'),

  update: (id, userDto) => request(`/api/users/${id}`, { method: 'PUT', body: userDto }),

  remove: (id) => request(`/api/users/${id}`, { method: 'DELETE' }),

  getAll: () => request('/api/users'),
};
