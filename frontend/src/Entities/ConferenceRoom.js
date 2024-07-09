import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import { Link, useNavigate } from "react-router-dom"; 
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form"; 
import DeleteIcon from "@mui/icons-material/Delete";
import IconButton from "@mui/material/IconButton"; 
import SiteServices from "../Service/SiteService";
import LocationServices from "../Service/LocationService";
import FloorServices from "../Service/FloorService";     
import RoomServices from "../Service/RoomService"; 
import { FcAcceptDatabase } from "react-icons/fc";
import { TbEdit } from "react-icons/tb";

import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

export default function ConferenceRoom({roomList}) {
  const initialRoomData = {
    id:"",
    type: "",
    description: "",
    capacity: "",  
    floorId: "", 
    siteId: "",
    cityId: "",
  };

  const filterRoomData = {
    cityId: "",
    siteId: "",
    floorId: "",
  };

  const navigate = useNavigate();
  const [rooms, setRooms] = useState([]);
  const [currentData, setCurrentData] = useState(initialRoomData);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [show, setShow] = useState(false); 
  const [viewCityData, setViewCityData] = useState([]);
  const [viewSiteData, setViewSiteData] = useState([]);
  const [viewFloorData, setViewFloorData] = useState([]);
  const [filterData, setFilterData] = useState(filterRoomData);

  useEffect(() => {
    getRoomsByFilter();
  }, [filterData.cityId, filterData.siteId, filterData.floorId]);

  const getRoomsByFilter = async () => {
    const result = await RoomServices.getRoomsByFilter(filterData.cityId, filterData.siteId, filterData.floorId);
    setRooms(result.data);
  };

  const BookRoom = (id) => {
    navigate(`/add-booking/${id}`, {
      state: { id: id },
    });
  };

  useEffect(() => {
    LocationServices.loadLocations()
      .then((response) => {
        setViewCityData(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log("error fetching cities", error);
      });
  }, []);

  const deleteRoom = async (id) => {
    const result = await RoomServices.deleteRoom(id);
    toast.success("room deleted Successfully!")
    getRoomsByFilter();
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;

    setCurrentData((prevData) => {
      const updatedData = { ...prevData, [name]: value };
      
      if (name === "cityId") {
        getSiteByCityId(value);
        setCurrentData((prevData) => ({ ...prevData, siteId: "", floorId: "" })); // Reset site and floor
      } else if (name === "siteId") {
        getFloorsBySiteId(value);
        setCurrentData((prevData) => ({ ...prevData, floorId: "" })); // Reset floor
      }

      return updatedData;
    });
  };

  const getRoom = async (id) => {
    handleShow();
    const result = await RoomServices.getRoom(id);
    console.log("getRoom:", result.data);
    const roomData=result.data;
    setCurrentData({
      id:roomData.id,
      type:roomData.type,
      description:roomData.description,
      capacity:roomData.capacity,floorId:roomData.floor?.id||"",
      siteId:roomData.site?.id||"",
      cityId:roomData.city?.id||"",
    });
    getSiteByCityId(roomData.city?.id);
    getFloorsBySiteId(roomData.site?.id);
  };

  const updateRoom = () => {
    console.log("updating with id", currentData);
    //const updatedInfo = { ...currentData, floorId: currentData.floorId, siteId: currentData.siteId, cityId: currentData.cityId };
    RoomServices.updateRoom(currentData.id, currentData)
      .then((response) => {
        toast.success("room details updated successfully");
        if (response.status === 200) {
          console.log(response.data);
          handleClose();
          getRoomsByFilter();
        }
      })
      .catch((error) => {
        console.log("error updating room data", error);
        toast.error(error.response?.data);
      });
  };

  const getSiteByLocation = () => {
    SiteServices.sitesInLocation(filterData.cityId).then((response) => {
      setViewSiteData(response.data);
    }).catch((error) => {
      console.log("error getting sites", error);
    });
  };

  useEffect(() => {
    if (filterData.cityId !== '') {
      getSiteByLocation();
    }
  }, [filterData.cityId]);

  const getSiteByCityId = (cityId) => {
    SiteServices.sitesInLocation(cityId).then((response) => {
      setViewSiteData(response.data);
    }).catch((error) => {
      console.log("error loading sites", error);
    });
  };

  const getFloorsBySiteId = (siteId) => {
    FloorServices.floorsBySite(siteId).then((response) => {
      setViewFloorData(response.data);
    }).catch((error) => {
      console.log("error loading floors", error);
    });
  };

  const getFloorsBySites = () => {
    FloorServices.floorsBySite(filterData?.siteId).then((response) => {
      setViewFloorData(response.data);
    }).catch((error) => {
      console.log("error getting floors", error);
    });
  };

  useEffect(() => {
    if (filterData.siteId !== '') {
      getFloorsBySites();
    }
  }, [filterData.cityId, filterData.siteId]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterData({ ...filterData, [name]: value });
  };

  console.log(currentData, "hii");

  return (
    <div style={{ width: "80%", margin: "20px auto" }}>
      <h2 style={{ fontWeight: "bold" }}>Rooms List</h2>
      <Form.Group className="mb-3">
        <Form.Select enabled value={filterData.cityId} onChange={handleFilterChange} name='cityId'>
          <option value="">Select City</option>
          {viewCityData.map((item) => (
            <option key={item.id} value={item.id}>{item.name}</option>
          ))}
        </Form.Select>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Select enabled value={filterData?.siteId} onChange={handleFilterChange} name='siteId'>
          <option value="">Select Site </option>
          {viewSiteData.map((item) => (
            <option key={item.id} value={item.id}>{item.siteId}</option>
          ))}
        </Form.Select>
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Select enabled value={filterData.floorId} onChange={handleFilterChange} name='floorId'>
          <option value="">Select Floor </option>
          {viewFloorData.map((item) => (
            <option key={item.id} value={item.id}>{item.floorId}</option>
          ))}
        </Form.Select>
      </Form.Group>
      <Table striped bordered hover variant="light">
        <thead style={{ backgroundColor: "#1976d2", height: "40px" }}>
          <tr>
            <th scope="row" style={{ textAlign: "center" }}>Id</th>
            <th scope="row" style={{ textAlign: "center" }}>Capacity</th>
            <th scope="row" style={{ textAlign: "center" }}>Description</th>
            <th scope="row" style={{ textAlign: "center" }}>Type</th>
            <th scope="row" style={{ textAlign: "center" }}>Location Name</th>
            <th scope="row" style={{ textAlign: "center" }}>Site</th>
            <th scope="row" style={{ textAlign: "center" }}>Floor</th>
            <th scope="row" style={{ textAlign: "center" }}>Action</th>
          </tr>
        </thead>
        <tbody>
          {rooms.map((row, index) => (
            <tr key={row.id}>
              <td style={{ textAlign: "center" }}>{index + 1}</td>
              <td style={{ textAlign: "center" }}>{row.capacity}</td>
              <td style={{ textAlign: "center" }}>{row.description}</td>
              <td style={{ textAlign: "center" }}>{row.type}</td>
              <td style={{ textAlign: "center" }}>{row.city?.name}</td>
              <td style={{ textAlign: "center" }}>{row.site?.siteId}</td>
              <td style={{ textAlign: "center" }}>{row.floor?.floorId}</td>
              <td style={{ textAlign: "center" }}>
                <IconButton
                  aria-label="book"
                  size="large"
                  onClick={() => BookRoom(row.id)}
                  color="info"
                >
                   <FcAcceptDatabase  fontSize="inherit"/> 
                </IconButton>
                <IconButton
                  aria-label="edit"
                  size="large"
                  onClick={() => getRoom(row.id)}
                  color="info"
                ><TbEdit fontSize="inherit" />
                 
                </IconButton>
                <IconButton
                  aria-label="delete"
                  size="large"
                  onClick={() => deleteRoom(row.id)}
                  color="error"
                >
                  <DeleteIcon fontSize="inherit" />
                </IconButton>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Link to="/add-room">
        <Button variant="primary">Add Room</Button>
      </Link>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title style={{ fontWeight: "bold" }}>Edit Room</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Select
              enabled value={currentData?.cityId || ""}
              onChange={handleInputChange}
              name="cityId"
              title="select City"
            >
              <option>Select City</option>
              {viewCityData.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Select
              enabled value={currentData?.siteId || ""}
              onChange={handleInputChange}
              name="siteId"
              title="select Site"
            >
              <option>Select Site</option>
              {viewSiteData.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.siteId}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Select
              enabled value={currentData?.floorId || ""}
              onChange={handleInputChange}
              name="floorId"
              title="select Floor"
            >
              <option>Select Floor</option>
              {viewFloorData.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.floorId}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
          <div className="form-group mb-2">
            <input
              name="capacity"
              className="form-control"
              value={currentData.capacity}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter capacity"
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
              name="type"
              className="form-control"
              value={currentData.type}
              onChange={handleInputChange}
              type="text"
              placeholder="Enter type"
            />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={updateRoom}>
            Update
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer/>
    </div>
  );
}