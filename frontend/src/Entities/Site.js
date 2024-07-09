import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import { Link } from "react-router-dom"; 
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form"; 
import DeleteIcon from "@mui/icons-material/Delete";
import IconButton from "@mui/material/IconButton";
import EditNoteIcon from "@mui/icons-material/EditNote";
import SiteServices from "../Service/SiteService";
import LocationServices from "../Service/LocationService"; 
import Pagination from '@mui/material/Pagination';

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { BsBuildingFillAdd } from "react-icons/bs";
 
export default function Site() {
  const initialSiteData = {
      
    siteId: "",
    description: "",
    pinCode: "",
     totalFloors: "",  
    cityId:"",
    
  }; 
   
  const [currentData, setCurrentData] = useState(initialSiteData);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [show, setShow] = useState(false);
  const [viewCityData, setViewCityData] = useState([]);

  const handleChange = (event, value) => {
    setOffset(value);
};
 

const [pageSize, setPageSize] = useState(5);
const [offset, setOffset] = useState(1);

const [count, setCount] = useState(0);
const getRequestParams = ( offset ,pageSize) => {
    let params = {}; 
    if (offset) {
      params["offset"] = offset - 1;
    }
    if (pageSize) {
      params["pageSize"] = pageSize;
    }
    return params;
};

 
const [sites, setSites] = useState([]);

useEffect(() => {
  loadSite();
  
}, [offset, pageSize]);
const loadSite = async()=>{
    const params = getRequestParams( 
        offset,
        pageSize
    );
    const result = await SiteServices.sitesUsingPagination(params);
    const{ totalPages,totalElements,size,content} = result.data.response;
    console.log("content:",result.data.response.content)
    if(content == 0 || content == undefined){
        setSites([]);
    }else{
        setSites(content);
      //  console.log("site:",sites.response);
    }
    setCount(totalPages); 
}
  
  const deleteSite = async (id) => {
    const result = await SiteServices.deleteSite(id);
    toast.success("site deleted successfully")
    loadSite();
  };
 
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    if (name === "cityId") {
      const selectedCity = viewCityData.find(
        (city) => city.id === parseInt(value)
      );
      setCurrentData({ ...currentData, city: selectedCity });
    } else {
      setCurrentData({ ...currentData, [name]: value });
    } 
  };
 
  
 
  const getSite = async (id) => {
    handleShow();
    const result = await  SiteServices.getSite(id);
    console.log("getSite:" ,result.data)
    setCurrentData(result.data);
  };
 
  const updateSite = () => {
    console.log("updating with id",currentData)
    const updatedData = { ...currentData, cityId: currentData.city?.id };  
    console.log(updatedData,"b")
     SiteServices.updateSite(currentData.id,updatedData)
      .then((response) => {
        toast.success("Site deatsils updated successfully")
        if (response.status === 200) {
          console.log(response.data);
          handleClose();
          loadSite();
        }
      })
      .catch((error) => {
        toast.error(error.response?.data);
        console.log("error", error);
      });
  };
 
  const getCities = () => {
    LocationServices.loadLocations()
      .then((response) => {
        setViewCityData(response.data);
        
      })
      .catch((error) => {
        console.log("error", error);
      });
  };
 
  useEffect(() => {
    getCities();
  }, []);
 
  console.log(currentData, "hii");
  return (
    <div style={{ width: "80%", margin: "20px auto" }}>
      <h2 style={{ fontWeight: "bold" }}>site List</h2>
      <Table stripped bordered hover variant="light">
        <thead style={{ backgroundColor: "#1976d2", height: "40px" }}>
          <th scope="row" style={{ textAlign: "center" }}>
             Id
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             Site Id
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             Description
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
            PinCode
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             Total Floors
          </th>
          <th scope="row" style={{ textAlign: "center" }}>
             location name
          </th>
          
          
          <th scope="row" style={{ textAlign: "center" }}>
            Action
          </th>
        </thead>
        <tbody>
          {sites?.map((row,index) => (
            <tr key={row.id}>
              <td   style={{ textAlign: "center" }}>
                {index+1}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.siteId}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.description}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.pinCode}
              </td>
              <td component="th" style={{ textAlign: "center" }}>
                {row.totalFloors}
              </td>
                 <td component="th" style={{ textAlign: "center" }}>
                   {row.city?.name}
                     </td>
               
               
              <td>
                <IconButton
                  aria-label="edit"
                  size="large"
                  onClick={() => getSite(row.id)}
                  color="info"
                ><BsBuildingFillAdd />
                  
                </IconButton>
                <IconButton
                  aria-label="delete"
                  size="large"
                  onClick={() => deleteSite(row.id)}
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
          <Link to="/add-site">
            <Button variant="primary">Add Site</Button>
          </Link>
        </div>
      </Table>
      <Pagination count={count} page={offset} onChange={handleChange} />
         
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title style={{ fontWeight: "bold" }}>Edit Site</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Select
              value={currentData?.city?.id || ""}
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
          </Form.Group>
          <div className="form-group mb-2">
            <input
              name="siteId"
              className="form-control"
              value={currentData.siteId}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter  Site  ID"
            />
          </div>
          <div className="form-group mb-2">
            <input
              name="description"
              className="form-control"
              value={currentData.description}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter description"
            />
          </div>
          <div className="form-group mb-2">
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
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={updateSite}>
            Update
          </Button>
        </Modal.Footer>
      </Modal>
    </div>   
  );
}