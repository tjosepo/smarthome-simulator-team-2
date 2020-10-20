import React from 'react';
import { Room, User } from '../../models';
import './style.scss';
import DoorIcon from '../../icons/door.svg';
import LightIcon from '../../icons/light.svg';
import PersonIcon from '../../icons/person.svg';
import WindowIcon from '../../icons/window.svg';
import WindowBlockedIcon from '../../icons/window-blocked.svg';

interface Props {
  rooms: Room[],
  users: User[]
}

function HouseView({ rooms, users }: Props) {
  let house: [Room[]] = [[]]; // Wierd syntax for 2D array.

  const render = (rooms: Room[]) => {
    house = [[]];
    rooms.forEach((room: Room) => {
      for (let y = house.length; house.length <= room.y; y++) {
        house.push(new Array<Room>());
      }
      house[room.y][room.x] = room;
    });
  }

  if (rooms) render(rooms);

  return (
    <div className="HouseView">
      {house[0].length > 0 ?
        <div className="grid">
          {house.map((row) =>
            row.map((room) => room
              && <div key={room.id} className="room" style={{ gridArea: `${room.y + 1} / ${room.x + 1} / span ${room.height || 1} / span ${room.width || 1}` }}>
                <div className="room-name">
                  {room.name}
                </div>

                <div className="room-doors">
                  {room.doors.map((door) =>
                    <img key={door.id} src={DoorIcon} alt="Door icon" />
                  )}
                </div>

                <div className="room-lights">
                  {room.lights.map((light) =>
                    <img key={light.id} src={LightIcon} alt="Light icon" />
                  )}
                </div>

                <div className="room-windows">
                  {room.windows.map((window) =>
                    <img key={window.id} src={window.blocked ? WindowBlockedIcon : WindowIcon} alt="Light icon" />
                  )}
                </div>

                <div className="room-people">
                  {users.map((user) =>
                    user.location?.id === room.id &&
                    <img key={user.id} src={PersonIcon} alt="Light icon" />
                  )}
                </div>
              </div>
            )
          )}
        </div>
        :
        <div className="empty-house text-muted">
          Not available
        </div>
      }
      <p className="description h6">House View</p>
    </div>
  );
}

export default HouseView;
