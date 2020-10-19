import React from 'react';
import { RoomLayout } from '../../models';
import './style.scss';

interface Props {
  houseLayout: RoomLayout[]
}

function HouseView({ houseLayout }: Props) {
  let house: [RoomLayout[]] = [[]]; // Wierd syntax for 2D array.

  const render = (rooms: RoomLayout[]) => {
    house = [[]];
    rooms.forEach((room: RoomLayout) => {
      for (let y = house.length; house.length <= room.y; y++) {
        house.push(new Array<RoomLayout>());
      }
      house[room.y][room.x] = room;
    });
  }

  if (houseLayout) render(houseLayout);

  return (
    <div className="HouseView">
      {house[0].length > 0 ?
        <div className="grid">
          {house.map((row) =>
            row.map((room) => room
              && <div className="room"
                style={{ gridArea: `${room.y + 1} / ${room.x + 1} / span ${room.heigth || 1} / span ${room.width || 1}` }}>
                {room.name}
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
