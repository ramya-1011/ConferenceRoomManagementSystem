import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; 
import Form from 'react-bootstrap/Form'; 
import SiteServices from '../../Service/SiteService';
import LocationServices from '../../Service/LocationService';

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { validateName, validateNumber } from '../../Validations';
 
const AddSite = () => {
    const initialSiteData ={
         
        siteId: "",
        description: "",
        pinCode: "",
        totalFloors: "",
        cityId:"",
        locationName:""
    };
      
    const navigate=useNavigate();
    const[currentData, setCurrentData] = useState(initialSiteData);
    const[viewCityData, setViewCityData] = useState([]);
 
 
    const handleInputChange = (event) => {
        const{name, value} = event.target;
        setCurrentData({...currentData,[name]:value});
    };
    const validateInputs = () => {
        const { siteId, description, pinCode, totalFloors, cityId } = currentData;
        if (!siteId || !description || !pinCode || !totalFloors || !cityId) {
            toast.error("All fields are required!");
            return false;
        }
         if(!/^[a-zA-Z0-9]*$/.test(siteId)){
            toast.error("site id can only contain letters or numbers");
            return false;
         }if(siteId.length>10)  {
            toast.error("site length cannot exceed 10 characters");
            return false;
         }
         if(description.length>15){
            toast.error("description length cannot exceed 15 characters");
            return false;
         }
        
        if (!/^\d{6}$/.test(pinCode)) {
            toast.error("Invalid Pin Code. It should be 6 digits.");
            return false;
        }
        if (!validateNumber(totalFloors)) {
            toast.error("Total Floors should be a number.");
            return false;
        }
        if (cityId === "") {
            toast.error("Please select a city.");
            return false;
        }
        return true;
    };

    const postSite = () =>{
        if (!validateInputs()) return;
        SiteServices.addSite(currentData).then((response)=>{
toast.success("site added successfully!")
            if(response.status === 200){
                setCurrentData(initialSiteData);
                console.log(response.data);
                navigate('/siteList')

                 
            }
        }).catch((error)=>{
            console.log("error", error);
            const errorMessage = error.response?.data || "An error occurred while adding the site.";
                toast.error(errorMessage);
             
        })
    }
 
    const getCities = () =>{
        LocationServices.loadLocations().then((response) =>{
          setViewCityData(response.data)
        }).catch((error) =>{
          console.log("error",error);
        })
    }
 
    useEffect(()=>{
        getCities();
    }, []);
 
  return (
    <div>
        <div className='container mt-5'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <h2 className='text-center' style={{fontWeight:"bold"}}> ADD Site</h2>
                        <div className='card-body'>
                                <Form.Group className="mb-3">
                                    <Form.Select enabled value={currentData.cityId} onChange={handleInputChange} name='cityId'>
                                    <option>--Select City--</option>
                                    {viewCityData.map((item) => (
                                        <option value={item.id}>{item.name}</option>
                                       
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                                <div className='form-group mb-2'>
                                    <input name='siteId' className='form-control'
                                    value={currentData.siteId}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter Site id' />
                                </div>
                                
                                <div className='form-group mb-2'>
                                    <input name='description' className='form-control'
                                    value={currentData.description}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter Description' />
                                </div>
                                <div className='form-group mb-2'>
                                    <input name='pinCode' className='form-control'
                                    value={currentData.pinCode}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter Pincode' />
                                </div>
                                <div className='form-group mb-2'>
                                    <input name='totalFloors' className='form-control'
                                    value={currentData.totalFloors}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Entertotal floors' />
                                </div>
                                
                                <button onClick={postSite} className='btn btn-success'>Save</button> {" "}
                                <Link to={"/siteList"} className='btn btn-danger' href=''>Cancel</Link>
                        </div>
                    </div>
                </div>
        </div>
 <ToastContainer/> 
    </div>
  )
}
 
export default AddSite;