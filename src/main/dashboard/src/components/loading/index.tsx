import React from 'react';
import './style.scss';

function Loading() {
  return (
    <div className="Loading">
      <div>
        <p className="lead">Please wait while the app is connecting to the server...</p>
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    </div>
  );
}

export default Loading;
