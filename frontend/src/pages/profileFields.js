/** Optional profile fields shared by the register and profile forms. */
export const optionalFields = [
  { name: 'phoneNumber', label: 'Telefon', type: 'tel', autoComplete: 'tel', placeholder: '+48 123 456 789' },
  { name: 'street', label: 'Ulica', autoComplete: 'street-address' },
  { name: 'postalCode', label: 'Kod pocztowy', autoComplete: 'postal-code', placeholder: '00-000' },
  { name: 'city', label: 'Miasto', autoComplete: 'address-level2' },
];

/** Backend validates optional fields with regexes - send null instead of "". */
export const emptyToNull = (obj) =>
  Object.fromEntries(Object.entries(obj).map(([k, v]) => [k, typeof v === 'string' && v.trim() === '' ? null : v]));
