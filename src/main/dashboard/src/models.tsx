export interface User {
  id: number,
  location?: Room,
  name: string,
  role: string,
}

export interface Room {
  id: number,
  name: string,
  x: number,
  y: number,
  width?: number,
  height?: number,
  windows: Window[],
  doors: Door[],
  lights: Light[]
  temperature: number,
  overridden: false,
  isHeating: boolean,
  isAc: boolean
}

export interface Window {
  id: number,
  opened: boolean,
  blocked: boolean
}

export interface Door {
  id: number,
  opened: boolean
}

export interface Light {
  id: number,
  on: boolean
}

export interface RoomLayout {
  name: string,
  x: number,
  y: number,
  width?: number,
  height?: number,
  windows?: number,
  doors?: number,
  lights?: number
}

export interface SHS {
  house: {
    rooms: Room[]
  },
  parameters: {
    users: User[]
  }
}

export interface HeatingZone {
  id: string;
  temperature: number;
  roomsIds: number[]
}