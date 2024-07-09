import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import { Link } from "react-router-dom"; 
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form"; 
import DeleteIcon from "@mui/icons-material/Delete";
import IconButton from "@mui/material/IconButton";
import StairsIcon from '@mui/icons-material/Stairs';
import SiteServices from "../Service/SiteService";
import LocationServices from "../Service/LocationService";
import FloorServices from "../Service/FloorService";

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
 
export default function Floor() {
  const initialFloorData = {
      
    siteId: "", 
    totalRooms: "",
    floorId:"",  
    cityId:"",
    
  }; 
   
 
  const [Floors, setFloors] = useState([]);
  const [currentData, setCurrentData] = useState(initialFloorData);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [show, setShow] = useState(false); 
  const [viewCityData, setViewCityData] = useState([]);
  const[viewSiteData,setViewSiteData]=useState([]); 

 useEffect(()=>{
  LocationServices.loadLocations()
  .then((response)=>{
    setViewCityData(response.data)
  })
  .catch((error) => {
    console.log("error fetching cities", error);
  });
 },[])
  useEffect(() => {
     
    loadFloor();
    
  }, [viewCityData],[viewSiteData]);
 
  const loadFloor = async () => {
    const result = await FloorServices.loadFloors();
    setFloors(result.data);
  };
  
 
  const deleteFloor = async (id) => {
    const result = await FloorServices.deleteFloor(id);
    toast.success("floor deleted successfully")
    loadFloor();
  };
 
  const handleInputChange = (e) => {
    const { name, value } = e.target;
   // console.log("input change",name,"value",value)
    if (name === "cityId" ) {
     
     setCurrentData({ ...currentData, cityId: value });
      SiteServices.sitesInLocation(value)
      .then((response) => { 
        setViewSiteData(response.data);
         
        
      })
      .catch((error) => {
        console.log("error loading sites", error);
      }); 
    } else {
      setCurrentData({ ...currentData, [name]: value });
    } 
  };
  const handleSiteChange=(e)=>{
    const { name, value } = e.target;
    setCurrentData({ ...currentData, [name]: value });
    

};
 
  
 
  const getFloor = async (id) => {
    handleShow();
    const result = await  FloorServices.getFloor(id);
    console.log("getFloor:" ,result.data)
    setCurrentData(result.data);
  };
 
  const updateFloor = () => {
    console.log("updating with id",currentData) 
   const updatedInfo = { ...currentData, cityId: currentData.cityId,siteId:currentData.siteId }; 
     FloorServices.updateFloor(currentData.id,updatedInfo)
      .then((response) => {
        toast.success("floor details updates successfully");
        if (response.status === 200) {
          console.log(response.data);
          handleClose();
          loadFloor();
        }
      })
      .catch((error) => {
        console.log("error", error);
      });
  };
  
   
  return (
    <div style={{ width: "80%", margin: "20px auto" }}>
      <h2 style={{ fontWeight: "bold" }}>Floor List</h2>
      <Table stripped bordered hover variant="light">
        <thead style={{ backgroundColor: "#1976d2", height: "40px" }}>
          <th scope="row" style={{ textAlign: "center" }}>
             Id
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             Floor Id
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
              Total Rooms
          </th> 
          
          <th scope="row" style={{ textAlign: "center" }}>
             location name
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             Site
          </th>
          
          
          <th scope="row" style={{ textAlign: "center" }}>
            Action
          </th>
        </thead>
        <tbody>
          {Floors.map((row,index) => (
            <tr key={row.id}>
              <td  style={{ textAlign: "center" }}>
                {index+1}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.floorId}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.totalRooms}
              </td> 
                <td component="th" style={{ textAlign: "center" }}>
                   {row.city?.name}
                 </td> 
                  <td component="th" style={{ textAlign: "center" }}>
                {row.site?.siteId}
              </td>
               
              <td>
                <IconButton
                  aria-label="edit"
                  size="large"
                  onClick={() => getFloor(row.id)}
                  color="info"
                >
                  <StairsIcon fontSize="inherit" />
                </IconButton>
                <IconButton
                  aria-label="delete"
                  size="large"
                  onClick={() => deleteFloor(row.id)}
                  color="error"
                >
                  <DeleteIcon fontSize="inherit" />
                </IconButton>
              </td>
            </tr>
          ))}
        </tbody>
         
        <div
          style={{
            display: "table-caption",
            width: "80%",
            margin: "20px auto",
          }}
        >
           
        </div>
        <Link to="/add-floor">
                    <Button variant="primary">Add Floor</Button>
                </Link>
      </Table>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title style={{ fontWeight: "bold" }}>Edit Floor</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Select
              value={currentData.cityId || ""}
              onChange={handleInputChange}
              name="cityId"
            >
              <option>Select City</option>{" "}
              {viewCityData.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
                  
                </option>
              ))}{" "}
            </Form.Select>
             
            <Form.Select
              value={currentData.siteId || ""}
              onChange={handleSiteChange}
              name="siteId"
            >
              <option>Select Site</option>{" "}
              {viewSiteData.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.siteId}
                  
                </option>
              ))}{" "}
            </Form.Select>
          </Form.Group>
          <div className="form-group mb-2">
            <input
              name="floorId"
              className="form-control"
              value={currentData.floorId}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter  floor ID"
            />
          </div>
          <div className="form-group mb-2">
            <input
              name="totalRooms"
              className="form-control"
              value={currentData.totalRooms}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter total Number of Rooms"
            />
          </div>
          {/* <div className="form-group mb-2">
            <input
              name="pinCode"
              className="form-control"
              value={currentData.pinCode}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter PinCode"
            />
          </div>
          <div className="form-group mb-2">
            <input
              name="totalFloors"
              className="form-control"
              value={currentData.totalFloors}
              onChange={handleInputChange}
              type="number"
              placeholder="Enter floors"
            />
          </div> */}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={updateFloor}>
            Update
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer/>
    </div>   
  );
}