import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; 
import Form from 'react-bootstrap/Form'; 
import SiteServices from '../../Service/SiteService';
import LocationServices from '../../Service/LocationService';
import FloorServices from '../../Service/FloorService';
import RoomServices from '../../Service/RoomService';

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { validateNumber } from '../../Validations';
 
const AddRoom = () => {
    const initialRoomData ={
         
      type:"",
      description:"",
      capacity:"",  
    floorId:"", 
    siteId:"",
    cityId:"", 
         
    };
    const navigate =useNavigate();
    const[currentData, setCurrentData] = useState(initialRoomData);
    const[viewCityData, setViewCityData] = useState([]);
    const[viewSiteData, setViewSiteData] = useState([]);
    const[viewFloorData, setViewFloorData] = useState([]);
 
 
    const handleInputChange = (e) => {
        const{name, value} = e.target;
        setCurrentData({...currentData,[name]:value});
    };

    const validateInputs = () => {
        const { floorId, type,capacity,description, siteId, cityId } = currentData;
        if (!siteId  ||!type ||!capacity ||!description || !cityId) {
            toast.error("All fields are required!");
            return false;
        }
         if(!type.length>15 ){
            toast.error("type cannot have more than 15 characters");
            return false;
         }if(description.length>15)  {
            toast.error("description length cannot exceed 15 characters");
            return false;
         } 
        if (!validateNumber(capacity)) {
            toast.error("capacity should be a number.");
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
        if (floorId === "") {
            toast.error("Please select a floor.");
            return false;
        }
        return true;
    };
 
    const postRoom = () =>{
        if (!validateInputs()) return;
         RoomServices.addRoom(currentData).then((response)=>{
            toast.success("room added successfully")
            if(response.status === 200){
                setCurrentData( initialRoomData
                );
                console.log(response.data);
                navigate('/RoomsList')
                
                 
            }
        }).catch((error)=>{
            console.log("error adding the room", error)
            toast.error(error.response?.data)
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
    };
    useEffect(()=>{
      getFloorsBySites();
    }, [currentData.siteId]);
    const getFloorsBySites=()=>{
      FloorServices.floorsBySite(currentData.siteId).then((response)=>{
        setViewFloorData(response.data)
      }).catch((error) => {
        console.log("error loading floors ", error);
    })
    };
    useEffect(()=>{
      getSiteByCityId  ();
    }, [currentData.cityId]);
 
  return (
    <div>
        <div className='container mt-5 mb-5'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <h2 className='text-center' style={{fontWeight:"bold"}}> Add Room</h2>
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
                            <Form.Group className="mb-3">
                                    <Form.Select enabled value={currentData.floorId} onChange={handleInputChange} name='floorId'>
                                    <option>Select Floor</option>
                                    {viewFloorData.map((item) => (
                                        <option value={item.id}>{item.floorId}</option>
                                       
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                                <div className='form-group mb-2'>
                                    <input name='type' className='form-control'
                                    value={currentData.type}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter type' />
                                </div>
                                
                                <div className='form-group mb-2'>
                                    <input name='description' className='form-control'
                                    value={currentData.description}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter  des' />
                                </div>
                                <div className='form-group mb-2'>
                                    <input name='capacity' className='form-control'
                                    value={currentData.capacity}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter  capacity' />
                                </div>
                              
                                
                                
                                <button onClick={postRoom} className='btn btn-success'>Save</button> {" "}
                                <Link to={"/RoomsList"} className='btn btn-danger' href=''>Cancel</Link>
                        </div>
                    </div>
                </div>
        </div>
 <ToastContainer/>
    </div>
  )
}
 
export default AddRoom;      