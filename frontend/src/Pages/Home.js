 import React, { useState } from 'react';
 
import Slider from '../Components/layout/Slider';
import { Link } from 'react-router-dom'; 
 import image_focus from '../Components/carouselImages/image_focus.png'
 import image_meeting from '../Components/carouselImages/image_meeting.png'
 import img5 from '../Components/carouselImages/img5.png' 
  
 
 const Home = () => {

   return (
 <div>
  <Slider></Slider>
  <section className='section'>
  <div className='container'>
    <div className='row'>
      <div className='col-md-12 text-center'>
      <h3 className='main-heading'>  Our Company</h3>
      <div className='underline mx-auto'></div>
      <p>  Welcome to Optum Conference Room Booking Management System,your go-to solutionfor hastle-free conference Room reservations. Our platform...</p>
       <Link to="/about" className='btn btn-warning shadow'> read more</Link>
    </div>
    </div>
  </div>

</section>
<section className='section bg-light'>
  <div className='container'>
    <div className='row'>
      <div className='col-md-12  mb-4  text-center'> 
      <h3 className='main-heading'> Our Services </h3>
      <div className='underline mx-auto'></div>
      </div > 
      <div className='col-md-4 '> 
        <div className='card shadow'>
        <img src={image_focus} className='w-100 border-bottom' alt='services'/>
        <div className='card-body'>
          <h5  > Service 1</h5>
          <h6> Easy Room Selection</h6>
          <div className='underline'></div>
          <p>Quickly browse and select rooms from different cities,sites, and types with just a click. Our user-friendly interface ensuresyou can find the rightroom effortlessly.</p>
          </div>
          </div> 
      </div>
      <div className='col-md-4 '> 
        <div className='card shadow'>
        <img src={image_meeting} className='w-100 border-bottom' alt='services'/>
        <div className='card-body'>
          <h5  > Service 2 </h5>
          <h6> Real-Time Availability</h6>
          <div className='underline text-center' ></div>
          
          <p>Stay informed with real-time availability updates. Our system display booked rooms along with their occupied time slots, preventing booking conflicts  and ensuring a smooth reservation process.</p>
          </div>
          </div>
          
      </div>
      <div className='col-md-4 '> 
        <div className='card shadow'>
        <img src={img5} className='w-100 border-bottom' alt='services'/>
        <div className='card-body'>
          <h6  > Service 3</h6>
          <div className='underline'></div>
          <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Itaque repudiandae sed vero incidunt eaque quam nesciunt fugit deserunt delectus corrupti commodi eligendi adipisci, aliquid reiciendis esse, cum culpa, ab quos!</p>
          </div>
          </div>
          
      </div>
         
        
       
     
       
    </div>

  </div>

</section>

 </div> 
     
      
      
   )
 }
 
 export default Home
 