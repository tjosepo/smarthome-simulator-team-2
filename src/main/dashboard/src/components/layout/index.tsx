import React from 'react';
import RoomLayout from '../../models/layout';
import './style.scss';
import json from './sample.json';

function Layout() {
  let house: [RoomLayout[]] = [[]]; // Wierd syntax for 2D array.
  
  const render = (rooms: RoomLayout[]) => {
    rooms.forEach((room: RoomLayout) => {
      // Extends the grid's Y axis if the room is outside the current grid.
      for (let y = house.length; house.length <= room.y; y++) {
        house.push(new Array<RoomLayout>());
      }
      house[room.y][room.x] = room;
      console.log(house);
    });
  }

  render(json as RoomLayout[]);

  return (
    <div className="Layout">
      {house.map((row) =>
        row.map((room) => room
          && <div className="room" 
            style={{gridArea: `${room.y + 1} / ${room.x + 1} / span ${room.heigth || 1} / span ${room.width || 1}`}}>
              {room.name}
            </div>
        )
      )}
    </div>
  );
}

export default Layout;
