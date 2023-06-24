export class Character {
    constructor(
        public life: number,
        public job: string,
        public diceValue: number[],
        public rerollLeft: number,
        public room: string
    ){}
}