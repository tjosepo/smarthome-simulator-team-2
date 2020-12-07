import React from 'react';
import { SHC, SHH, SHP, SHS } from './modules';
import { User, Room } from '../../models';
import 'bootstrap/js/dist/tab.js';
import './style.scss';

interface Props {
  simulating: boolean,
  awayMode: boolean,
  users: User[],
  rooms: Room[],
  loggedAsId: number,
  setUsers: React.Dispatch<React.SetStateAction<User[]>>,
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>,
  setOutsideTemp: React.Dispatch<React.SetStateAction<number>>,
  setLoggedAsId: React.Dispatch<React.SetStateAction<number>>,
  setAwayMode: React.Dispatch<React.SetStateAction<boolean>>,
}

function ModuleController({ simulating, users, awayMode, rooms, loggedAsId, setUsers, setRooms, setLoggedAsId, setAwayMode, setOutsideTemp }: Props) {
  return (
    <div className="ModuleController">
      <ul className="nav nav-tabs" id="moduleTab" role="tablist">
        <li className="nav-item" role="presentation">
          <a className="nav-link active" id="shs-tab" data-toggle="tab" href="#shs" role="tab" aria-controls="home" aria-selected="true">SHS</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="shc-tab" data-toggle="tab" href="#shc" role="tab" aria-controls="shc" aria-selected="false">SHC</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="shp-tab" data-toggle="tab" href="#shp" role="tab" aria-controls="shp" aria-selected="false">SHP</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="shh-tab" data-toggle="tab" href="#shh" role="tab" aria-controls="shh" aria-selected="false">SHH</a>
        </li>
      </ul>
      <div className="tab-content card" id="moduleTabContent">
        <div className="tab-pane card-body fade show active" id="shs" role="tabpanel" aria-labelledby="shs-tab">
          <SHS {...{ simulating, users, setUsers, setRooms, setLoggedAsId, setOutsideTemp }} />
        </div>
        <div className="tab-pane card-body fade" id="shc" role="tabpanel" aria-labelledby="shc-tab">
          <SHC {...{ rooms, setRooms }} />
        </div>
        <div className="tab-pane card-body fade" id="shp" role="tabpanel" aria-labelledby="shp-tab">
          <SHP {...{ awayMode, setAwayMode, rooms, setRooms }} />
        </div>
        <div className="tab-pane card-body fade" id="shh" role="tabpanel" aria-labelledby="shh-tab">
          <SHH {...{ rooms, loggedAsId }} />
        </div>
      </div>
    </div>
  );
}

export default ModuleController;
