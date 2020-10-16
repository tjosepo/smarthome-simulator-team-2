import React, { useState } from 'react';
import { SimulationContext, ModuleController, HouseView } from '../';
import { User, RoomLayout } from '../../models';
import './style.scss';

function Dashboard() {
  const [simulating, setSimulating] = useState<boolean>(false);
  const [users, setUsers] = useState<User[]>([]);
  const [houseLayout, setHouseLayout] = useState<RoomLayout[]>([]);

  return (
    <div className="Dashboard container-fluid">
      <SimulationContext {...{ simulating, setSimulating, users }} />
      <ModuleController {...{ simulating, users, setUsers, setHouseLayout }} />
      <HouseView {...{ houseLayout }} />
    </div>
  );
}

export default Dashboard;
