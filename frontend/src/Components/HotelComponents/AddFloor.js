import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; 
import Form from 'react-bootstrap/Form'; 
import SiteServices from '../../Service/SiteService';
import LocationServices from '../../Service/LocationService';
import FloorServices from '../../Service/FloorService';
import { validateNumber } from '../../Validations';

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
 
const AddFloor = () => {
    const initialFloorData ={
         
        siteId: "",
        floorId: "", 
        totalRooms: "",
        cityId:"",
         
    };
    const navigate=useNavigate();
    const[currentData, setCurrentData] = useState(initialFloorData);
    const[viewCityData, setViewCityData] = useState([]);
    const[viewSiteData, setViewSiteData] = useState([]);
 
 
    const handleInputChange = (e) => {
        const{name, value} = e.target;
        setCurrentData({...currentData,[name]:value});
    };
    const validateInputs = () => {
        const { floorId, totalRooms, siteId, cityId } = currentData;
        if (!siteId || !floorId || !totalRooms || !cityId) {
            toast.error("All fields are required!");
            return false;
        }
         if(!/^[a-zA-Z0-9]*$/.test(floorId)){
            toast.error("site id can only contain letters or numbers");
            return false;
         }if(floorId.length>10)  {
            toast.error("floor Id length cannot exceed 10 characters");
            return false;
         }
            
         
        if (!validateNumber(totalRooms)) {
            toast.error("Total Rooms should be a number.");
            return false;
        }
        if (cityId === "") {
            toast.error("Please select a city.");
            return false;
        }
        if (siteId === "") {
            toast.error("Please select a site.");
            return false;
        }
        return true;
    };

 
    const postFloor = () =>{
        if (!validateInputs()) return;
        FloorServices.addFloor(currentData).then((response)=>{
            if(response.status === 200){
                setCurrentData(initialFloorData);
                console.log(response.data);
                navigate('/FloorsList')

                 
            }
        }).catch((error)=>{
            console.log("error adding the floor", error)
        })
    }
 
    const getCities = () =>{
        LocationServices.loadLocations().then((response) =>{
          setViewCityData(response.data)
        }).catch((error) =>{
          console.log("error getting cities",error);
        })
    }
 
    useEffect(()=>{
        getCities();
    }, []);

    const getSiteByCityId = () =>{
         SiteServices.sitesInLocation(currentData.cityId).then((response) =>{
            setViewSiteData(response.data)
        }).catch((error) => {
            console.log("error loading sites ", error);
        })
    }
    useEffect(()=>{
        getSiteByCityId();
    }, [currentData.cityId]);
 
  return (
    <div>
        <div className='container mt-5 mb-5'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <h2 className='text-center' style={{fontWeight:"bold"}}> ADD Floor</h2>
                        <div className='card-body'>
                                <Form.Group className="mb-3">
                                    <Form.Select enabled value={currentData.cityId} onChange={handleInputChange} name='cityId'>
                                    <option>Select City</option>
                                    {viewCityData.map((item) => (
                                        <option value={item.id}>{item.name}</option>
                                       
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                                <Form.Group className="mb-3">
                                <Form.Select enabled value={currentData.siteId} onChange={handleInputChange} name='siteId'>
                                <option>Select Site</option>
                                {viewSiteData.map((item) => (
                                    <option value={item.id}>{item.siteId}</option>
                                   
                                    ))}
                                </Form.Select>
                            </Form.Group>
                                <div className='form-group mb-2'>
                                    <input name='floorId' className='form-control'
                                    value={currentData.floorId}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter floor id' />
                                </div>
                                
                                <div className='form-group mb-2'>
                                    <input name='totalRooms' className='form-control'
                                    value={currentData.totalRooms}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter total Rooms' />
                                </div>
                              
                                
                                
                                <button onClick={postFloor} className='btn btn-success'>Save</button> {" "}
                                <Link to={"/floorsList"} className='btn btn-danger' href=''>Cancel</Link>
                        </div>
                    </div>
                </div>
        </div>
 <ToastContainer/>
    </div>
  )
}
 
export default AddFloor;      