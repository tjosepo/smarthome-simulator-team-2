import React from 'react';
import ReactDOM from 'react-dom';
import Dashboard from './components/dashboard';
import 'bootstrap/dist/css/bootstrap.min.css';
import './style.scss';

ReactDOM.render(
  <React.StrictMode>
    <Dashboard />
  </React.StrictMode>,
  document.getElementById('root')
);