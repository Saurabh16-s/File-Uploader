import logo from './logo.svg';



import './App.css';
import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios';
 

const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState([]);
 
  const fetchUserProfiles = () => {
    axios.get("http://localhost:8081/api/v1/user-profile")
      .then(res => {
        console.log(res);
        setUserProfiles(res.data);
      });
  };
 
  useEffect(() => {
    fetchUserProfiles();
  }, []);
 
  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
 
        
        <div className="poster">
 
          {/* WANTED */}
          <div className="wanted-text">WANTED</div>
 
          {/* PHOTO FRAME */}
          <div className="poster-img-wrapper">
            <img
              className="poster-img"
              src={`http://localhost:8081/api/v1/user-profile/${userProfile.userProfileId}/image/download?${Date.now()}`}
              alt="profile"
            />
          </div>
 
      
          <div className="doa-section">
            <div className="doa-row">
              <span className="curl"></span>
              <span className="dead-text">DEAD OR ALIVE</span>
              <span className="curl flip"></span>
            </div>
          </div>
 
     
          <div className="name-text">{userProfile.username?.toUpperCase()}</div>
 
          <div className="bounty-row">
            <span className="berry-symbol">฿</span>
            <span className="bounty-amount">1,000,000,000—</span>
          </div>
 
      
          <div className="bottom-curls">
            <span className="curl"></span>
            <span className="curl flip"></span>
          </div>
 
          
          <div className="marine-footer">
            <div className="fine-print">
              KONO SAKUHIN WA FICTION DE ARIMASU.<br />
              JITSUZAISURU JINBUTSU DANTAI<br />
              SONOTA NO SOSHIKI TO WA ISSAI<br />
              MUKANKEIDESU.
            </div>
            <div className="marine-text">MARINE</div>
          </div>
 
        </div>
 
        {/* 🔥 UPLOAD ZONE — outside poster */}
        <div className="upload-box">
          <Dropzone userProfileId={userProfile.userProfileId} />
        </div>
 
      </div>
    );
  });
};
 
function Dropzone({ userProfileId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    const formData = new FormData();
    formData.append("file", file);
 
    axios.post(
      `http://localhost:8081/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    )
    .then(() => {
      console.log("File uploaded successfully");
      window.location.reload();
    })
    .catch(err => {
      console.log(err);
    });
  }, [userProfileId]);
 
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });
 
  return (
    <div
      {...getRootProps()}
      style={{
        border: "2px dashed #8b6914",
        padding: "20px",
        cursor: "pointer",
        width: "380px",
        margin: "0 auto 40px",
        color: "#c9a96e",
        fontFamily: "'Cinzel', serif",
        letterSpacing: "2px",
        fontSize: "13px",
        background: "rgba(0,0,0,0.3)",
        borderRadius: "4px"
      }}
    >
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>DROP THE FILES HERE ...</p> :
          <p>DRAG & DROP OR CLICK TO UPLOAD</p>
      }
    </div>
  );
}
 

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}
 
export default App;