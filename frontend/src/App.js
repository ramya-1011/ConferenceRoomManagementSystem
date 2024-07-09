 
import { BrowserRouter as  Router,Route, Routes } from 'react-router-dom';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import react from 'react';

import './App.css';
import NavBar from './Components/layout/NavBar';
import Home from './Pages/Home';
import About from './Pages/About';
import Contact from './Pages/Contact';  
import Footer from './Pages/Footer'; 
import AddLocation from './Components/HotelComponents/AddLocation';   
import AddBooking from './Components/HotelComponents/AddBooking';
import AddRoomComponent from './Components/HotelComponents/AddRoomComponent';
import Location from './Entities/Location';
import Site from './Entities/Site';
import AddSite from './Components/HotelComponents/AddSite';
import Floor from './Entities/Floor';
import AddFloor from './Components/HotelComponents/AddFloor';
import ConferenceRoom from './Entities/ConferenceRoom';
import BookingListComponent from './Entities/BookingListComponent';
 

function App() {
  return (
    <div className="App">
       
       <Router>
     <div>
     <NavBar></NavBar>
      <Routes>
      <Route exact path="/" Component={Home}></Route>
       
          <Route exact path="/About" Component={About}></Route>
          <Route exact path="/Contact" Component={Contact}></Route>
          <Route exact path="/LocationList" Component= {Location}></Route>
          <Route exact path="/siteList" Component={Site}></Route>
          <Route exact path="/add-Location" Component={AddLocation}></Route>
          <Route exact path="/add-site" Component={AddSite}></Route>
          <Route exact path="/add-booking" Component={AddBooking}></Route>
          <Route exact path="/add-booking/:id" Component={AddBooking}></Route>
          <Route exact path="/BookingList" Component= {BookingListComponent}></Route>
          <Route exact path="/RoomsList" Component={ConferenceRoom}></Route>
          <Route exact path="/FloorsList" Component={Floor}></Route>
          <Route exact path="/add-room" Component={AddRoomComponent}></Route>
          <Route exact path="/add-floor" Component={AddFloor}></Route>
          <Route exact path="/edit-room/:id" Component={AddRoomComponent}></Route> 
           
          </Routes>
           
     </div>
      
       
     <Footer></Footer>
       
      
      </Router>
       

    </div>
  );
}

export default App;
