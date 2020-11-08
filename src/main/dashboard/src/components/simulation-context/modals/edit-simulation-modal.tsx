import React from 'react';
import { User, Room, SHS } from '../../../models';
import { POST_MOVE_USERS, POST_BLOCK_WINDOWS } from '../../../queries';

interface Props {
  users: User[],
  rooms: Room[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>,
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>
}

function EditSimulationModal({ users, setUsers, rooms, setRooms }: Props) {

  const editSimulation = async (e: React.FormEvent) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const data = new FormData(form);

    if (users.length > 0) moveUsers(data);
    if (rooms.length > 0) blockWindows(data);

    (form.querySelector('[data-dismiss="modal"]') as HTMLElement).click();
    form.reset();
  }


  const moveUsers = async (data: FormData) => {
    const response = await fetch(POST_MOVE_USERS, {
      method: 'POST',
      body: data
    });

    const shs = await response.json() as SHS;
    const users = shs.parameters.users;
    const rooms = shs.house.rooms;
    setUsers(users);
    setRooms(rooms);
  }

  const blockWindows = async (data: FormData) => {
    const response = await fetch(POST_BLOCK_WINDOWS, {
      method: 'POST',
      body: data
    });

    const rooms = await response.json() as Room[];

    setRooms(rooms);
  }

  return (
    <div className="EditSimulationModal modal fade" id="EditSimulationModal" tabIndex={-1} aria-labelledby="EditSimulationModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={(e) => editSimulation(e)}>
          <div className="modal-header">
            <h5 className="modal-title" id="EditSimulationModalLabel">Edit Simulation</h5>
          </div>
          <div className="modal-body">

            <h5>Move Users</h5>
            {users.length > 0 ?
              users.map((user) =>
                <div key={`moveUser${user.id}`} className="row mb-2">
                  <label className="col-sm-4 col-form-label" htmlFor={`LocationUser${user.id}`}>{user.name}</label>
                  <div className="col-sm-8">
                    <select className="form-select" aria-label="Default select example" id={`LocationUser${user.id}`} name={`locationUser${user.id}`} required defaultValue="-1">
                      <option value="-1">Outside</option>
                      {rooms.map((room) =>
                        <option key={room.id} value={room.id}>{room.name}</option>
                      )}
                    </select>
                  </div>
                </div>
              )
              :
              <p>No users to move.</p>
            }

            <h5>Block Windows</h5>
            {rooms.length > 0 ?
              rooms.map((room) =>
                <div key={`blockRoom${room.id}`}>
                  <h6>{room.name}</h6>
                  {rooms.length > 0 ?
                    room.windows.map((window) =>
                      <div key={`blockWindow${window.id}`} className="form-check">
                        <input className="form-check-input" type="checkbox" value="true" id={`BlockWindow${window.id}`} name={`blockWindow${window.id}`} defaultChecked={window.blocked} />
                        <label className="form-check-label" htmlFor={`BlockWindow${window.id}`}>
                          Block window {window.id}
                        </label>
                      </div>
                    )
                    :
                    <p>No windows in {room.name}</p>
                  }
                </div>
              )
              :
              <p>No windows to block.</p>
            }
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
            <button type="submit" className="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditSimulationModal;
