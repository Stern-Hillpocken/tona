import { Character } from "./character.model";
import { User } from "./user.model";

export class PodRegister {
    constructor(
        public name: string,
        public difficulty: number,
        public captain: User,
        //public crew: Character[],
        public characterMax: number,
        public status: string,
        public password?: string
    ){}
}