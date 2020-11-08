import React from 'react';
import { Room } from '../../../../models';
import { POST_TOGGLE_LIGHT, POST_TOGGLE_WINDOW, POST_TOGGLE_DOOR, POST_TOGGLE_AUTO_MODE } from '../../../../queries';
import './style.scss';

interface Props {
  rooms: Room[],
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>,
}

function SHC({ rooms, setRooms }: Props) {

  const toggleLight = async (id: number, checked: boolean) => {
    const data = new FormData();
    data.append('id', id.toString());
    data.append('isOn', checked.toString());

    const response = await fetch(POST_TOGGLE_LIGHT, {
      method: 'POST',
      body: data
    });

    const rooms = await response.json() as Room[];
    setRooms(rooms);
  }

  const toggleWindow = async (id: number, checked: boolean) => {
    const data = new FormData();
    data.append('id', id.toString());
    data.append('isOpened', checked.toString());

    const response = await fetch(POST_TOGGLE_WINDOW, {
      method: 'POST',
      body: data
    });

    const rooms = await response.json() as Room[];
    setRooms(rooms);
  }

  const toggleDoor = async (id: number, checked: boolean) => {
    const data = new FormData();
    data.append('id', id.toString());
    data.append('isOpened', checked.toString());

    const response = await fetch(POST_TOGGLE_DOOR, {
      method: 'POST',
      body: data
    });

    const rooms = await response.json() as Room[];
    setRooms(rooms);
  }

  const toggleAutoMode = async (checked: boolean) => {
    const data = new FormData();
    data.append('autoMode', checked.toString());

    fetch(POST_TOGGLE_AUTO_MODE, {
      method: 'POST',
      body: data
    });
  }

  return (
    <div className="SHC">
      <h5 className="card-title">Smart Home Core</h5>
      <ul className="nav nav-pills mb-3" id="pills-tab" role="tablist">
        <li className="nav-item" role="presentation">
          <a className="nav-link active" id="pills-lights-tab" data-toggle="pill" href="#pills-lights" role="tab" aria-controls="pills-lights" aria-selected="true">Lights</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="pills-windows-tab" data-toggle="pill" href="#pills-windows" role="tab" aria-controls="pills-windows" aria-selected="false">Windows</a>
        </li>
        <li className="nav-item" role="presentation">
          <a className="nav-link" id="pills-doors-tab" data-toggle="pill" href="#pills-doors" role="tab" aria-controls="pills-doors" aria-selected="false">Doors</a>
        </li>
      </ul>

      <div className="tab-content" id="pills-tabContent">
        <div className="tab-pane fade show active" id="pills-lights" role="tabpanel" aria-labelledby="pills-lights-tab">
          {rooms.length > 0
            ? <>
              <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" id="awayModeSwitch" onChange={(e) => toggleAutoMode(e.target.checked)} />
                <label className="form-check-label" htmlFor="awayModeSwitch">Auto mode</label>
              </div>
              {rooms.map((room) => room.lights.length > 0
                ? <div key={room.id} className="room">
                  <h6>{room.name}</h6>
                  {room.lights.map((light) =>
                    <div key={light.id} className="form-check form-switch">
                      <input className="form-check-input" type="checkbox" checked={light.on} id={`toggleLight${light.id}`} onChange={(e) => toggleLight(light.id, e.target.checked)} />
                      <label className="form-check-label" htmlFor={`toggleLight${light.id}`}>Open light {light.id}</label>
                    </div>
                  )}
                </div>
                : <div key={room.id}>
                </div>
              )}
            </>
            : <p>No lights to interact with.</p>
          }
        </div>

        <div className="tab-pane fade" id="pills-windows" role="tabpanel" aria-labelledby="pills-windows-tab">
          {rooms.length > 0
            ? rooms.map((room) => room.windows.length > 0
              ? <div key={room.id} className="room">
                <h6>{room.name}</h6>
                {room.windows.map((window) =>
                  <div key={window.id} className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" checked={window.opened} id={`toggleWindow${window.id}`} onChange={(e) => toggleWindow(window.id, e.target.checked)} />
                    <label className="form-check-label" htmlFor={`toggleWindow${window.id}`}>Open window {window.id}</label>
                  </div>
                )}
              </div>
              : <div key={room.id}>
              </div>
            )
            : <p>No windows to interact with.</p>
          }
        </div>

        <div className="tab-pane fade" id="pills-doors" role="tabpanel" aria-labelledby="pills-doors-tab">
          {rooms.length > 0
            ? rooms.map((room) => room.doors.length > 0
              ? <div key={room.id} className="room">
                <h6>{room.name}</h6>
                {room.doors.map((door) =>
                  <div key={door.id} className="form-check form-switch">
                    <input className="form-check-input" type="checkbox" checked={door.opened} id={`toggleDoor${door.id}`} onChange={(e) => toggleDoor(door.id, e.target.checked)} />
                    <label className="form-check-label" htmlFor={`toggleDoor${door.id}`}>Open door {door.id}</label>
                  </div>
                )}
              </div>
              : <div key={room.id}>
              </div>
            )
            : <p>No doors to interact with.</p>
          }
        </div>
      </div>
    </div>
  );
}

export default SHC;
