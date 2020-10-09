import React from 'react';
import SimulationController from '../simulation-controller';
import ModuleController from '../module-controller';
import './style.scss';

function Dashboard() {
  return (
    <div className="Dashboard container-fluid">
      <SimulationController />
      <ModuleController />
    </div>
  );
}

export default Dashboard;
