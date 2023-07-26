import { Room } from "./room.model";

export class Pod {
    constructor(
        public health: number,
        public maxHealth: number,
        public rooms: Room[]
    ){}
}