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

