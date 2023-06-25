export class Character {
    constructor(
        public life: number,
        public job: string,
        public dicePool: number[],
        public diceStocked: number[],
        public rerollLeft: number,
        public room: string
    ){}
}