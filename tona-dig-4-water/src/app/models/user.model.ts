import { Majagaba } from "./majagaba.model";

export class User {
    constructor(
        public pseudo: string,
        public email: string,
        public profilePicture: string,
        public character: Majagaba
    ){}
}