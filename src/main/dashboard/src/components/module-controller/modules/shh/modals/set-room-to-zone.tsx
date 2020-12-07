import React from 'react';
import { HeatingZone, Room } from '../../../../../models';
import { POST_SET_ROOM_TO_ZONE } from '../../../../../queries';

interface Props {
  rooms: Room[],
  setZones: React.Dispatch<React.SetStateAction<HeatingZone[]>>,
  zoneToSet: string
}

function SetRoomToZoneModal({ rooms, setZones, zoneToSet }: Props) {
  const setRoom = async (e: React.FormEvent) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const data = new FormData(form);

    if (!data.get('roomId')) return;

    const response = await fetch(POST_SET_ROOM_TO_ZONE, {
      method: 'POST',
      body: data
    });

    const zones = await response.json() as HeatingZone[];
    setZones(zones);
    (form.querySelector('[data-dismiss="modal"]') as HTMLElement).click();
    form.reset();
  }

  return (
    <div className="SetRoomToZone modal fade" id="SetRoomToZoneModal" tabIndex={-1} aria-labelledby="SetRoomToZoneModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={(e) => setRoom(e)}>
          <input type="hidden" name="zoneId" value={zoneToSet} />
          <input type="hidden" name="option" value="add" />

          <div className="modal-header">
            <h5 className="modal-title" id="SetRoomToZoneModal">Set Room</h5>
          </div>

          <div className="modal-body">
            <div className="mb-2">
              <label htmlFor="RoomId" className="form-label">Room</label>
              <select className="form-select" id="RoomId" name="roomId" required defaultValue="">
                <option value="" hidden></option>
                {rooms.map(room =>
                  <option key={room.id} value={room.id}>{room.name}</option>
                )}
              </select>
            </div>
          </div>

          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
            <button type="submit" className="btn btn-primary">Set</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SetRoomToZoneModal;
