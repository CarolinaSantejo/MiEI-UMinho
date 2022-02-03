import enquirer from 'enquirer'
import Table from 'cli-table3'
import SportIcon from '../common/sport_icon.js';



export default {
    drawEventTable,
    showFeedOptions,
    showFilterScreen,

    showSelectEventScreen,
    showSelectTeamOddScreen,
    showSelectCoinScreen,
    showBetAmmountScreen
};


// function show 
function drawEventTable(events) {
    events.map((obj) => {

            const table = new Table({
                head: betTableHeader(),
                colWidths: [8, 8, 8, 8, 8,8,8,8,8],
                // chars: { 'mid': '', 'left-mid': '', 'mid-mid': '', 'right-mid': '' }
            });

            const sportIcon = SportIcon.getSportIcon(obj.sport);
            const sportName = obj.sport //`//`${obj.sport.charAt(0).toUpperCase()}${obj.sport.substring(1, obj.sport.length)}`
            console.log(`==> ${sportIcon} ${sportName}`)


            const eventList = obj.events;
            eventList.map((evnt) => {
                table.push(betTableRow(evnt))
            })

            console.log(table.toString());
            console.log('\n');
    });
}

function showFeedOptions() {
    console.log("\n")

    const questions = [
        {
            type: 'select',
            name: 'option',
            message: 'Options:',
            choices: [
                { name: 'PLACE_BET', message: 'Place a Bet' },
                { name: 'BETTING_SLIP', message: 'My Betting Slip' },
                { name: 'FILTER', message: 'Filter/Search Bets' },
                { name: 'CLEAR_FILTER', message: 'Clear Filter' },
                { name: 'RETURN', message: 'Return to Menu' }
            ]
        }
    ];
    return enquirer.prompt(questions)
}

function showFilterScreen() {
    const questions = [
        {
            type: 'select',
            name: 'filterType',
            message: 'Available Actions: ',
            choices: [
                { name: 'TEAM', message: 'Filter by team name' },
                { name: 'DATE', message: 'Filter by date' },
                { name: 'SPORT', message: 'Filter by sport' },
            ]
        },

        {
            type: 'input',
            name: 'filterSearch',
            message: 'Enter you search:',
        }
    ];

    return enquirer.prompt(questions)
}

/* ============================================ */
/*             Betting Screen                   */

function showSelectEventScreen(events) {
    console.clear();
    drawEventTable(events);

    const questions = [
        {
            type: 'numeral',
            name: 'selectedEventNumber',
            message: 'Enter the event number:',
        }
    ];

    return enquirer.prompt(questions);
}

function showSelectTeamOddScreen(event) {
    console.clear();
    console.log(event)
    console.log(`Event ID: ${event.id}`);
    console.log(`Date: ${event.date}`);
    console.log(`Start Time: ${event.startTime}`);
    console.log(`${event.teamA} vs ${event.teamB}`);

    console.log("\nAvailable bets: ");

    console.log("==> 1X2")
    draw1x2BetTable(event, event.bets._1x2)

    const questions = [
        {
            type: 'select',
            name: 'selectedTeam',
            message: 'Options:',
            choices: [
                { name: 'TEAM_1', message: `Bet on ${event.teamA}` },
                { name: 'TIE', message: `Bet on Tie` },
                { name: 'TEAM_2', message: `Bet on ${event.teamB}` },
            ]
        }
    ];
    return enquirer.prompt(questions)
}

function showSelectCoinScreen() {
    console.clear();

    const questions = [
        {
            type: 'select',
            name: 'selectedCoin',
            message: 'Which coin should be used?',
            choices: [
                { name: 'EURO', message: 'Euro', value: 'Euro' },
                { name: 'USD', message: 'USD', value: 'USD' },
                { role: 'separator' },
                { name: 'BITCOIN', message: 'Bitcoin', value: 'Bitcoin' },
                { name: 'ETHEREUM', message: 'Ethereum', value: 'Ethereum' },
            ]
        }
    ];
    return enquirer.prompt(questions)
}

function showBetAmmountScreen() {
    console.clear();

    const questions = [
        {
            type: 'numeral',
            name: 'enteredAmount',
            message: 'Enter the amount you want bet:',
        }
    ];

    return enquirer.prompt(questions);
}



/* ============================================ */
/*             Table drawing helpers            */

function betTableHeader() {
    return [
        { hAlign: 'center', content: 'Nrº' },
        { hAlign: 'center', content: 'Date' },
        { hAlign: 'center', content: 'Start Time' },
        { hAlign: 'center', content: 'Teams' },
        { hAlign: 'center', colSpan: 3, colWidths: [null, 8], content: 'Odds' }
    ]
}

function betTableRow(evnt) {
    return [
        { hAlign: 'center', content: `42` },
        { hAlign: 'center', content: `${evnt.date}` },
        { hAlign: 'center', content: `${evnt.startTime}` },
        { hAlign: 'center', content: `${evnt.teamA} vs ${evnt.teamB}` },

        { hAlign: 'center', content: `${evnt.bets._1x2.odd1}` },
        { hAlign: 'center', content: `${evnt.bets._1x2.oddX}` },
        { hAlign: 'center', content: `${evnt.bets._1x2.odd2}` }

    ]

}

function draw1x2BetTable(event, eventOdds) {
    const tableHeader = [
        { hAlign: 'center', content: `${event.teamA} Odd` },
        { hAlign: 'center', content: `Tie Odd` },
        { hAlign: 'center', content: `${event.teamB} Odd` },
    ]

    const table = new Table({
        head: tableHeader,
        colWidths: [16, 16, 16]
        // chars: { 'mid': '', 'left-mid': '', 'mid-mid': '', 'right-mid': '' }
    });

    const tableRow = [
        { hAlign: 'center', content: `${eventOdds.odd1}` },
        { hAlign: 'center', content: `${eventOdds.oddX}` },
        { hAlign: 'center', content: `${eventOdds.odd2}` },
    ]

    table.push(tableRow);

    console.log(table.toString());
}