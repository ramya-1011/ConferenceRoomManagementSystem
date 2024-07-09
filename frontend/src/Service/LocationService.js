import commonURL from "../commonURL";

 
 
 
const addLocation = (data) => {
    return commonURL.post("/cities/add-city", data);
};
 
// const getUserIssuedBooks = (id) => {
//     return httpcommon.get(`/api/users/user/${id}`);
// };
 
// const returnBook = (id) => {
//     return httpcommon.post(`/api/book/return/${id}`);
// };
 
const deleteLocation = (id) => {
    return commonURL.delete(`/cities/delete/${id}`);
};
 
const getLocation = (id) => {
    return commonURL.get(`/cities/byId/${id}`);
};
 
const loadLocations = () => {
    return commonURL.get("cities/citiesList");
};
 
const updateLocation = (id, data) => {
    return commonURL.put(`/cities/update/${id}`, data);
};
 
const LocationServices = {
    addLocation,
      
    deleteLocation,
    getLocation,
    loadLocations,
     
    updateLocation,
}
 
export default LocationServices;
 