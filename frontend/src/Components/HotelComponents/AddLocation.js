 
import React, { useState, useEffect } from 'react';
import { Link,      useNavigate } from 'react-router-dom';   
import LocationServices from '../../Service/LocationService'; 
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import Form from "react-bootstrap/Form"; 
import { validateName, validateNumber } from '../../Validations';

const AddLocation = () => {
     
        const navigate = useNavigate();
        const  initialCityData={
            name: '',
            state: '',
            totalSites: '',
        } 
        const[errors, setErrors] = useState({
            name: "",
        state: "",
        totalSites: "",
        },[]);  
          const[currentData,setCurrentData]=useState(initialCityData); 
        const handleInputChange = (e) => {
            const{name, value} = e.target;
            setErrors((prevErrors) => ({
                ...prevErrors,
                [name]: "",
            }));
            setCurrentData({...currentData,[name]:value});
        };
        const validateFields = () => {
            let isValid = true;
            let newErrors = { name: "", state: "", totalSites: "" };
    
            if (!currentData.name) {
                newErrors. name = "City name is required.";
                
                isValid = false;
            } else if (!validateName(currentData.name)) {
                newErrors. name = "City name must contain only letters.";
                 
                isValid = false;
            }
    
            if (!currentData.state) {
                newErrors.state = "State is required.";
                 
                isValid = false;
            } else if (! validateName(currentData.state)) {
                newErrors.state = "State must contain only letters.";
                 
                isValid = false;
            }
    
            if (!currentData.totalSites) {
                newErrors.totalSites = "Total sites is required.";
                
                isValid = false;
            } else if (! validateNumber(currentData.totalSites)) {
                newErrors.totalSites = "Total sites must contain only numbers.";
                
                isValid = false;
            }
    
            setErrors(newErrors);
            return isValid;
        };
         
        const AddLocation = async () => {
            if (!validateFields()) {
                return;
            } 
            
            LocationServices.addLocation(currentData)
                .then((response) => {
                    toast.success("City created successfully!");
                    if (response.status === 200) {
                        setCurrentData(initialCityData);
                         
                        navigate('/LocationList')
                    }
                })
                .catch((error) => {
                    console.log("error", error);
                    if (error.response && error.response.status === 400) {
                        const errorResponse = error.response.data;
                        if (errorResponse.errorState) {
                            setErrors({});
                            Object.keys(errorResponse.errorState).forEach((key) => {
                                const errorMessage = errorResponse.errorState[key][0];
                                setErrors((prevErrors) => ({
                                    ...prevErrors,
                                    [key]: errorMessage,
                                }));
                            });
                        } else {
                            setErrors({});
                        }
                    }
                });
             
             
                
        };
        
    
    
  return (
    <div>
    <div className='container mt-5 mb-5'>
            <div className='row'>
                <div className='card col-md-6 offset-md-3'>
                    <h2 className='text-center' style={{fontWeight:"bold"}}> ADD CITY</h2>
                    <div className='card-body'>
                            <div className='form-group mb-2'> 
                                <Form>
                                    <Form.Group className="mb-3" controlId="formTaskName"> 
                                    <Form.Control
                                        required
                                        type="text"
                                        placeholder="Enter City name"
                                        name="name"
                                        value={currentData.name}
                                            onChange={handleInputChange}/>
                                        <Form.Control.Feedback type="invalid">
                                        {errors.name}
                                    </Form.Control.Feedback>
                                    <span style={{display: "flex", color: "red"}}>{errors.name}</span>
                                    </Form.Group>  
                                </Form>
                                <Form>
                                    <Form.Group className="mb-3" controlId="formTaskName">
                                     
                                    <Form.Control
                                        required
                                        type="text"
                                        placeholder="Enter State name"
                                        name="state"
                                        value={currentData.state}
                                            onChange={handleInputChange}/>
                                        <Form.Control.Feedback type="invalid">
                                        {errors.state}
                                    </Form.Control.Feedback>
                                    <span style={{display: "flex", color: "red"}}>{errors.state}</span>
                                    </Form.Group>  
                                </Form>
                                <Form>
                                    <Form.Group className="mb-3" controlId="formTaskName">
                                    {/* <Form.Label>{("City Name")}</Form.Label> */}
                                    <Form.Control
                                        required
                                        type="text"
                                        placeholder="Enter total Sites"
                                        name="totalSites"
                                        value={currentData.totalSites}
                                            onChange={handleInputChange}/>
                                        <Form.Control.Feedback type="invalid">
                                        {errors.totalSites}
                                    </Form.Control.Feedback>
                                    <span style={{display: "flex", color: "red"}}>{errors.totalSites}</span>
                                    </Form.Group>  
                                </Form>
                            </div>
                            <button onClick={AddLocation} className='btn btn-success'>Save</button> {" "}
                            <Link to={"/LocationList"} className='btn btn-danger' href='/'>Cancel</Link>
                    </div>
                </div>
            </div>
        </div>
    <ToastContainer />
    </div>
     
       
    
  )
}

export default AddLocation
