import React, { useEffect, useState } from 'react'
import Table from 'react-bootstrap/Table';
import { useNavigate } from 'react-router-dom'; 
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom'; 
import LocationServices from '../Service/LocationService'; 
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { MdEditLocationAlt } from "react-icons/md";
import IconButton from "@mui/material/IconButton"; 
import DeleteIcon from "@mui/icons-material/Delete";

 

  export default function Location  () {
    const navigate = useNavigate(); 
    const [location, setLocation] = useState([]);
    const [currentData, setCurrentData] = useState([]);
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    useEffect(()=>{
        loadlocation();
    }, []);
 
    const loadlocation = async()=>{
        try{
            const result = await LocationServices.loadLocations();
        setLocation(result.data);
        }catch (error){
            console.error('error loading locations',error);
            toast.error('failed to load locations');
        }
    
    } 
    const deleteLocation = async(id) => {
        const result = await LocationServices.deleteLocation(id);
        toast.success('location deleted successfully');
        loadlocation();
     
    }
 
    const getLocation = async(id) => {
        handleShow();
        const result = await LocationServices.getLocation(id);
        setCurrentData(result.data);
    }; 
 
    const updateLocation = () =>{
        
        
        LocationServices.updateLocation(currentData.id, currentData).then((response)=>{
            toast.success('location updated successfully')
            if(response.status === 200){ 
                
                console.log(response.data);
                handleClose(); 
                loadlocation();
               

            }
        }).catch((error)=>{
            console.error("error updating location", error);
            toast.error('failed to update location')
        })
    }
 
    const handleInputChange = (event) => {
        const{name, value} = event.target;
        setCurrentData({...currentData,[name]:value});
    };
 
    
 
  return (
    <div  style={{ width: '80%', margin: '20px auto' }}>
        <h2 style={{ fontWeight: 'bold' }}>location List</h2>
        <Table stripped bordered hover variant="light">
            <thead style={{backgroundColor:"#1976d2",height:"40px"}}>
                <th style={{textAlign:"center"}}> Id</th>
                <th style={{textAlign:"center"}}>City Name</th>
                <th style={{textAlign:"center"}}>State</th>
                <th style={{textAlign:"center"}}>Total Sites</th> 
                <th style={{textAlign:"center"}}>Action</th>
            </thead>
            <tbody>
                {
                    location?.map((row,index) =>
                    (
                        <tr key={row.id}>
                            <td  style={{textAlign:"center"}}>
                                {index+1}
                            </td>
                            <td component="th" style={{textAlign:"center"}}>
                                {row.name}
                            </td>
                            <td component="th" style={{textAlign:"center"}}>
                                {row.state}
                            </td>
                            <td component="th" style={{textAlign:"center"}}>
                                {row.totalSites}
                            </td> 
                            <td> {" "}
                            <IconButton
                            variant="info" onClick={() => getLocation(row.id)}
                            > <MdEditLocationAlt />
                                
                            
                            </IconButton>
                            <IconButton
                             aria-label="delete"
                             size="large"
                             onClick={() => deleteLocation(row.id)}
                             color="error">
<DeleteIcon fontSize="inherit" />
                            </IconButton>
                            
                             
                            </td>
                        </tr>
                    ))
                }
            </tbody>
            <div  style={{display:'table-caption', width: '80%', margin: '20px auto'}}>
            <Link to="/add-Location">
                    <Button variant="primary">Add Location</Button>
                </Link>
            </div>
        </Table>
        <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Location</Modal.Title>
        </Modal.Header>
        <Modal.Body><div className='form-group mb-2'>
                                    <input name='name' className='form-control'
                                    value={currentData.name}
                                    
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter Name' />
                                </div>
                                <div className='form-group mb-2'>
                                    <input name='state' className='form-control'
                                    value={currentData.state}
                                    onChange={handleInputChange}
                                        type="text" placeholder='Enter state' />
                                </div>
                                <div className='form-group mb-2'>
                                    <input name='totalSites' className='form-control'
                                    value={currentData.totalSites}
                                    onChange={handleInputChange}
                                        type="Number" placeholder='Enter totalSites' />
                                </div> 
                                 </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={updateLocation}>
            Update Location
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer/>
    </div>
  )
}

 
