import { Character } from "./character.model";

export class User {
    constructor(
        public pseudo: string,
        public email: string,
        public profilePicture: string,
        public character: Character
    ){}
}