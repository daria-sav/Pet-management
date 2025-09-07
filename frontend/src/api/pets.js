import http from './http';

export const listPets = () => http.get('/pets');
export const getPet = (id) => http.get(`/pets/${id}`);        
export const createPet = (payload) => http.post('/pets', payload);
export const updatePet = (id, payload) => http.put(`/pets/${id}`, payload);
export const deletePet = (id) => http.delete(`/pets/${id}`);
export const checkIdentification = (value, excludeId) =>
    http.get('/pets/check-identification', {
        params: { value, excludeId }
    });