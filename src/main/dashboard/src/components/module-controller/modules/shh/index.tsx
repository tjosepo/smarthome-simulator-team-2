import React, { FormEvent, Fragment, useState } from 'react';
import { HeatingZone, Room } from '../../../../models';
import { POST_SET_ROOM_TO_ZONE, POST_SET_TEMPERATURE, POST_SET_ZONE } from '../../../../queries';
import SetRoomToZoneModal from './modals/set-room-to-zone';
import './style.scss';

interface Props {
  rooms: Room[],
  loggedAsId: number
}

function SHH({ rooms, loggedAsId }: Props) {
  const [zones, setZones] = useState<HeatingZone[]>([]);
  const [zoneToSet, setZoneToSet] = useState<string>("");

  const createZone = async (e: FormEvent) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const data = new FormData(form);
    data.append("loggedAs", loggedAsId.toString())


    if (!data.get("zoneId")) return;

    const response = await fetch(POST_SET_ZONE, {
      method: 'POST',
      body: data
    });

    const zones = await response.json();
    setZones(zones);
    form.reset();
  }

  const setTemperature = async (e: FormEvent) => {
    e.preventDefault();

    const input = e.target as HTMLInputElement;
    const form = input.closest('form') as HTMLFormElement;
    const data = new FormData(form);
    data.append("loggedAs", loggedAsId.toString())

    if (!data.get("temperature")) return;

    const response = await fetch(POST_SET_TEMPERATURE, {
      method: 'POST',
      body: data
    });

    const zones = await response.json();
    setZones(zones);
  }

  const removeRoom = async (id: number) => {
    const data = new FormData();
    data.append('roomId', id.toString());
    data.append('option', 'remove');
    data.append("loggedAs", loggedAsId.toString())


    const response = await fetch(POST_SET_ROOM_TO_ZONE, {
      method: 'POST',
      body: data
    })

    const zones = await response.json() as HeatingZone[];
    setZones(zones);
  }

  return (
    <div className="SHH">
      <h5 className="card-title">Smart Home Heater</h5>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="WinterTemperature">Summer temperature</label>
        <div className="col-sm-8">
          <form onChange={setTemperature} className="input-group">
            <input type="hidden" name="option" value="summerTemperature" />
            <input id="WinterTemperature" type="number" className="form-control" name="temperature" aria-label="Temperature" defaultValue="15" />
            <span className="input-group-text">°C</span>
          </form>
          <div className="input-group">

          </div>
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Winter temperature</label>
        <div className="col-sm-8">
          <div className="input-group">
            <input id="SummerTemperature" type="number" className="form-control" aria-label="Temperature" defaultValue="15" />
            <span className="input-group-text">°C</span>
          </div>
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Set room temperature</label>
        <div className="col-sm-8">
          <form onSubmit={setTemperature} className="input-group mb-3">
            <input type="hidden" name="option" value="roomTemperature" />
            <select className="form-select" id="remperatureRoomId" name="roomId" required defaultValue="">
              <option value="" hidden></option>
              {rooms.map(room =>
                <option key={room.id} value={room.id}>{room.name}</option>
              )}
            </select>
            <input type="number" className="form-control" placeholder="" name="temperature" />
            <button type="submit" className="btn btn-outline-primary">Set</button>
          </form>
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Add heating zone</label>
        <div className="col-sm-8">
          <form onSubmit={createZone} className="input-group mb-3">
            <input type="hidden" name="option" value="add" />
            <input type="text" className="form-control" placeholder="" name="zoneId" />
            <button type="submit" className="btn btn-outline-primary">Add</button>
          </form>
        </div>
      </div>


      {zones?.map(zone =>
        <div key={zone.id} className="mb-2">
          <h5>{zone.id}</h5>

          <div className="row mb-2">
            <label className="col-sm-4 col-form-label" htmlFor="Temperature">Target temperature</label>
            <div className="col-sm-8">
              <form onChange={setTemperature} className="input-group">
                <input type="hidden" name="option" value="zoneTemperature" />
                <input type="hidden" name="zoneId" value={zone.id} />
                <input type="number" className="form-control" defaultValue="21" name="temperature" />
                <span className="input-group-text">°C</span>
              </form>
            </div>
          </div>

          <div className="row mb-2">
            <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Period 1</label>
            <div className="col-sm-8">
              <div className="input-group">
                <input id="Period1" type="number" className="form-control" aria-label="Temperature" />
                <span className="input-group-text">°C</span>
              </div>
            </div>
          </div>

          <div className="row mb-2">
            <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Period 2</label>
            <div className="col-sm-8">
              <div className="input-group">
                <input id="Period2" type="number" className="form-control" aria-label="Temperature" />
                <span className="input-group-text">°C</span>
              </div>
            </div>
          </div>

          <div className="row mb-2">
            <label className="col-sm-4 col-form-label" htmlFor="SummerTemperature">Period 3</label>
            <div className="col-sm-8">
              <div className="input-group">
                <input id="Period3" type="number" className="form-control" aria-label="Temperature" />
                <span className="input-group-text">°C</span>
              </div>
            </div>
          </div>

          <div className="list-group">
            <a href="#SetRoomToZoneModal" className="list-group-item list-group-item-action text-secondary" data-toggle="modal" onClick={() => setZoneToSet(zone.id)}>
              Add room
            </a>
            {rooms.map(room =>
              <div key={`zone-${zone.id}-room-${room.id}`}>
                {zone.roomsIds.includes(room.id)
                  ? <div className="list-group-item">
                    <p className="h6 mb-0">{room.name}</p>
                    <button className="btn btn-link text-danger" onClick={() => removeRoom(room.id)}>Remove</button>
                  </div>
                  : <></>
                }
              </div>
            )}
          </div>
        </div>
      )}

      <Fragment>
        <SetRoomToZoneModal {...{ rooms, setZones, zoneToSet }} />
      </Fragment>
    </div>
  );
}

export default SHH;
