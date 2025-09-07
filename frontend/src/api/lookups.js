import http from './http';

export const getPetTypes = () => http.get('/lookups/pet-types');
export const getCountries = () => http.get('/lookups/countries');