export class Majagaba {
    constructor(
        public id: number,
        public life: number,
        public maxLife: number,
        public job: string,
        public dicePool: number[],
        public diceStocked: number[],
        public diceNextTurn: number[],
        public rerollLeft: number,
        public room: string,
        public steamBlast: number,
        public steamRegulator: number,
        public steamSwitcher: number,
        public powerCharge: number,
        public powerChargeMax: number
    ){}
}