import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom'; 
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import RoomServices from '../../Service/RoomService';
import { validateName } from '../../Validations';
 
const AddBooking = () => {
    const location = useLocation();
    const path = location.state;
 
    const initialBookingData ={
        
            roomId:path?.id, 
        confirmationCode:"",
        employeeName: '',
         employeeId:'',
         employee_ph_no:'',
         bookingDate:'',
         startTime:'',
         endTime:'',
         attendees:'',
        
    };
    const[currentData, setCurrentData] = useState(initialBookingData);
 
    const handleInputChange = (event) => {
        const{name, value} = event.target;
        setCurrentData({...currentData,[name]:value});
    };
    const {roomId,employeeId,employeeName,employee_ph_no,bookingDate,startTime,endTime,attendees } = currentData;
    const validateInputs = () => {
    if ( !roomId|| !employeeId || !employeeName || !employee_ph_no || !bookingDate ||!startTime ||!endTime || !attendees) {
        toast.error("All fields are required!");
        return false;
    }
    if(!validateName(employeeName)){
        toast.error("employee name can only contain letters")
    }
    return true;
}
    const  saveBooking = () =>{
        if (!validateInputs()) return;
         RoomServices.addBooking( currentData).then((response)=>{
           
          console.log(currentData)
            toast.success("Booked successfully!!!");
            if(response.status === 200){
                setCurrentData(initialBookingData);
                console.log(response.data);
            }
        }).catch((error)=>{
            toast.error(error.response?.data);
            console.log("error", error)
        })
    }
 
  return (
    <div>
    <div className='container mt-5'>
            <div className='row'>
                <div className='card col-md-6 offset-md-3'>
                    <h2 className='text-center' style={{fontWeight:"bold"}}>  Book Room</h2>
                    <div className='card-body'>
                        <div className='form-group mb-2'>
                            <input name='roomId' className='form-control' disabled value={path?.id}
                                type="text" placeholder='Enter Room Id' />
                        </div>
                        {/* <div className='form-group mb-2'>
                            <input name='title' className='form-control' disabled value={path?.title}
                                type="text" placeholder='Enter Book Name' />
                        </div> */}
                        <div className='form-group mb-2'>
                            <input name='bookingDate' className='form-control'
                            value={currentData.bookingDate}
                            onChange={handleInputChange}
                                type="date" placeholder='Enter booking date' />
                        </div>
                        {/* <div className='form-group mb-2'>
                            <input name='confirmationCode' className='form-control'
                            value={currentData.confirmationCode}
                            onChange={handleInputChange}
                                type="text" placeholder='Enter Confirmation Code' />
                        </div> */}
                        <div className='form-group mb-2'>
                            <input  name="employeeName" className='form-control'
                            value={ currentData.employeeName}
                            onChange={handleInputChange}
                                type="text" placeholder='Enter name' />
                        </div>
                        <div className='form-group mb-2'>
                            <input name="employeeId"  className='form-control'
                              value={ currentData.employeeId}
                            onChange={handleInputChange}
                                type="text" placeholder='Enter id' />
                        </div>
                        <div className='form-group mb-2'>
                            <input   name="employee_ph_no"  className='form-control'
                                value={ currentData.employee_ph_no}
                            onChange={handleInputChange}
                                type="text" placeholder='Enter phno' />
                        </div>
                        <div className='form-group mb-2'>
                            <input   name="startTime"  className='form-control'
                                value={ currentData.startTime}
                            onChange={handleInputChange}
                                type="time" placeholder='Enter Confirmation Code' />
                        </div>
                        <div className='form-group mb-2'>
                            <input   name="endTime"  className='form-control'
                                value={ currentData.endTime}
                            onChange={handleInputChange}
                                type="time" placeholder='Enter end time' />
                        </div>
                        <div className='form-group mb-2'>
                            <input   name="attendees"  className='form-control'
                                value={ currentData.attendees}
                            onChange={handleInputChange}
                                type="text" placeholder='Enter attendees' />
                        </div>
                        
                        {/* <div className='form-group mb-2'>
                            <input name='expiryDate' className='form-control'
                            value={currentData.expiryDate}
                            onChange={handleInputChange}
                                type="date" title='Enter Expiry Date' />
                        </div> */}
                        <button onClick={ saveBooking} className='btn btn-success'>Save</button> {" "}
                        <Link to={"/RoomsList"} className='btn btn-danger' href=''>Cancel</Link>
                    </div>
                </div>
            </div>
        </div>
    <ToastContainer/>
</div>
  )
}
 
export default AddBooking;