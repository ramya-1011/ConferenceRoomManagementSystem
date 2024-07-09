
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom' ;
import Table from 'react-bootstrap/Table';
import axios from 'axios';
import BookingServices from '../Service/BookingService';

import DeleteIcon from "@mui/icons-material/Delete";
import IconButton from "@mui/material/IconButton"; 
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

const BookingListComponent = () => {
    const navigate=useNavigate();
  const[bookings,setBookings] = useState([])
  useEffect(()=>{
    getAllConferenceRooms();
     
   },[])
   const getAllConferenceRooms = async()=>{
    const result = await axios.get("http://localhost:8080/booking/all");
    setBookings(result.data.bookingsList);
    console.log(result.data)
};

const deleteBooking = async (id) => {
    const result = await BookingServices.deleteBooking(id);
    toast.success("booking deleted Successfully!");
          navigate('/RoomsList')                                
  };
  return (
    <div className='container'>
     
   <Link to={"/find-booking"} className='btn btn-primary mb-2 mt-3' href="">find booking!!</Link>
     <h2 className='text-center'> LIST OF BOOKINGS</h2>
     <Table stripped bordered hover variant="light">
         <thead style={{backgroundColor:"#1976d2",height:"50px"}}>
             <th scope="row" style={{textAlign:"center"}}> Booking Id   </th>
             <th scope="row" style={{textAlign:"center"}}> Room Id </th>
             <th scope="row" style={{textAlign:"center"}}>Employee Name </th>
             <th scope="row" style={{textAlign:"center"}}>Employee Id </th>
             <th scope="row" style={{textAlign:"center"}}>Phone Number </th>
             <th scope="row" style={{textAlign:"center"}}>Booking Date  </th>
             <th scope="row" style={{textAlign:"center"}}>Start Time  </th>
             <th scope="row" style={{textAlign:"center"}}>End Time </th>
             <th scope="row" style={{textAlign:"center"}}>attendees </th>
             <th scope="row" style={{textAlign:"center"}}> confirmationCode</th>
             <th scope="row" style={{textAlign:"center"}}> actions</th>
             
             
            
         </thead>
         <tbody>
             {
                    bookings?.map((row) =>
                 (
                     <tr key={row.id}>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.bookingID}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.roomId}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.employeeName}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.employeeId}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.employee_ph_no}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.bookingDate}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.startTime}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.endTime}
                         </td>
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                             {row.attendees}
                         </td>
                         
                         <td component="th" scope="row" style={{textAlign:"center"}}>
                                {row.confirmationCode}
                            </td>
                            <IconButton
                  aria-label="delete"
                  size="large"
                  onClick={() => deleteBooking(row.bookingID)}
                  color="error"
                >
                  <DeleteIcon fontSize="inherit" />
                </IconButton>
                         
                     </tr>
                 ))
             }
         </tbody>
     </Table>
      <ToastContainer/>
 </div>
  )
}

export default BookingListComponent
