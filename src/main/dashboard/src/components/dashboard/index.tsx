import React, { useState } from 'react';
import { SimulationContext, ModuleController, HouseView } from '../';
import { User, Room } from '../../models';
import './style.scss';

function Dashboard() {
  const [simulating, setSimulating] = useState<boolean>(false);
  const [users, setUsers] = useState<User[]>([]);
  const [rooms, setRooms] = useState<Room[]>([]);

  return (
    <div className="Dashboard container-fluid">
      <SimulationContext {...{ simulating, setSimulating, users, setUsers, rooms }} />
      <ModuleController {...{ simulating, users, setUsers, setRooms }} />
      <HouseView {...{ users, rooms }} />
    </div>
  );
}

export default Dashboard;
