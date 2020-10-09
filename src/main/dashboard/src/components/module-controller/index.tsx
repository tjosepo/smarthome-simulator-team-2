import React from 'react';
import 'bootstrap/js/dist/tab.js';
import './style.scss';

function ModuleController() {
  return (
    <div className="ModuleController">
      <ul className="nav nav-tabs" id="moduleTab" role="tablist">
        <li className="nav-item" role="presentation">
          <a className="nav-link active" id="shs-tab" data-toggle="tab" href="#shs" role="tab" aria-controls="home" aria-selected="true">SHS</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Profile</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Contact</a>
        </li>
      </ul>
      <div className="tab-content card" id="moduleTabContent">
        <div className="tab-pane card-body fade show active" id="shs" role="tabpanel" aria-labelledby="shs-tab">
          <h5 className="card-title">Special title treatment</h5>
          <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
          <button className="btn btn-primary">Go somewhere</button>
        </div>
        <div className="tab-pane card-body fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
        <div className="tab-pane card-body fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
      </div>
    </div>
  );
}

export default ModuleController;
