import { Room } from "./room.model";

export class Pod {
    constructor(
        public health: number,
        public rooms: Room[]
    ){}
}