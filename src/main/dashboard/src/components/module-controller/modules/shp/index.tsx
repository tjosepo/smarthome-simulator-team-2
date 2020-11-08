import React from 'react';
import { Room } from '../../../../models';
import { POST_AWAY_MODE, POST_KEEP_LIGHT_ON } from '../../../../queries';
import './style.scss';

interface Props {
  awayMode: boolean,
  rooms: Room[],
  setAwayMode: React.Dispatch<React.SetStateAction<boolean>>,
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>
}

function SHP({ awayMode, setAwayMode, rooms, setRooms }: Props) {

  const toggleAwayMode = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target;

    const data = new FormData();
    data.append('awayMode', input.checked.toString());

    const response = await fetch(POST_AWAY_MODE, {
      method: 'POST',
      body: data
    });

    const obj = await response.json();
    const rooms = obj.house.rooms as Room[];
    const awayMode = obj.awayMode as boolean;

    setAwayMode(awayMode);
    setRooms(rooms);
  }

  const keepLightOn = async (id: number, keepOn: boolean) => {
    const data = new FormData();
    data.append('id', id.toString());
    data.append('keepOn', keepOn.toString());

    const response = await fetch(POST_KEEP_LIGHT_ON, {
      method: 'POST',
      body: data
    });

    const rooms = await response.json() as Room[];

    setRooms(rooms);
  }

  return (
    <div className="SHP">
      <h5 className="card-title">Smart Home Security</h5>
      <div className="form-check form-switch mb-2">
        <input className="form-check-input" type="checkbox" id="awayModeSwitch" checked={awayMode} onChange={toggleAwayMode} />
        <label className="form-check-label" htmlFor="awayModeSwitch">Away mode</label>
      </div>
      {rooms.length > 0
        ? <>
          {rooms.map((room) => room.lights.length > 0
            ? <div key={room.id} className="room mb-2">
              <h6>{room.name}</h6>
              {room.lights.map((light) =>
                <div key={light.id} className="form-check form-switch">
                  <input className="form-check-input" type="checkbox" id={`toggleLight${light.id}`} onChange={(e) => keepLightOn(light.id, e.target.checked)} />
                  <label className="form-check-label" htmlFor={`toggleLight${light.id}`}>Keep on light {light.id}</label>
                </div>
              )}
            </div>
            : <div key={room.id}>
            </div>
          )}
        </>
        : <p>No lights to interact with.</p>
      }

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="keepOnFrom">Keep on from</label>
        <div className="col-sm-8">
          <input type="time" className="form-control" id="keepOnFrom" />
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="keepOnUntil">Keep on until</label>
        <div className="col-sm-8">
          <input type="time" className="form-control" id="keepOnUntil" />
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="alertPoliceAfter">Alert police after</label>
        <div className="col-sm-8">
          <div className="input-group">
            <input id="alertPoliceAfter" type="number" className="form-control" />
            <span className="input-group-text">minutes</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SHP;
