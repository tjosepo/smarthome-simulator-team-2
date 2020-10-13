import React from 'react';
import SimulationController from '../simulation-controller';
import ModuleController from '../module-controller';
import Layout from '../layout';
import './style.scss';

function Dashboard() {
  return (
    <div className="Dashboard container-fluid">
      <SimulationController />
      <ModuleController />
      <Layout />
    </div>
  );
}

export default Dashboard;
