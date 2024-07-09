import React from 'react' 

const About = () => {
  return (
    <div > 
      <section className='py-4 bg-info'>  
      <div className='container'>
        <div className='row'>
          <div className='col-md-4 my-auto'>
            <h4> About Us</h4> 
          </div>
          <div className='col-md-8 my-auto'>
            <h6 > Home/ About Us</h6> 
          </div>
        </div> 
      </div> 
      </section>
      <section className='section bg-light border-bottom'>
        <div className='container'>
          <h5 className='main-heading'> Our Company
          <div className='underline'></div>
          </h5>
          <p> Welcome to Optum Conference Room Booking Management System,your go-to solutionfor hastle-free conference Room reservations. Our platform streamlines  the booking process, allowing you to easily find and reserve the perfect room for your meetings,conferences and events.
            We are committed to providing a seamless and efficient booking experience,ensuring that you have access to the best facilities ,regardless of your location.
          </p>
        </div>

      </section>
        
     </div>
     
  )
}

export default About
