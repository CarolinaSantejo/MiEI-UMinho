import feedView from '../../view/user/feed_view.js';

import acknowledgePrompts from '../../view/common/common_prompts.js';
import utils from '../../view/common/utils.js';

import bettingSlipController from '../../controller/user/betting_slip_controller.js'
import event_service from '../../service/user/event_service.js';


export default {
    feed
};


let events = []

async function feed() {

    try {
        const serverResponse = await event_service.getEventFeed();
        events = serverResponse;



        while (true) {
            // utils.clearScreen();
            console.log("===================================== Feed =====================================");
            console.log(events);

            
            feedView.drawEventTable(filteredEvents);
            
           
            const { option } = await feedView.showFeedOptions();

            if (option === 'PLACE_BET') {
                await placeBetScreen();
            }
            else if (option === 'BETTING_SLIP') {
                await bettingSlipScreen();
            }
            else if (option === 'FILTER') {
                await filterScreen();
            }
            else if (option === 'CLEAR_FILTER') {
                currentFilter.date = '';
                currentFilter.team = '';
                currentFilter.sport = '';
                filteredEvents = Array.from(fetchedEvents);
            }
            else
                break;
        }
    } catch (error) {

    }



}

async function placeBetScreen() {
    /*
        Things needed to place a bet:
        1. Select a bet
        2. Select a team/odd
        3. Select the coin to use
        4. Enter an amount
        5. submit bet
    */

    /*


    // Ask the user for the number/id of the event he wants to bet on
    const { selectedEventNumber } = await feedView.showSelectEventScreen(filteredEvents);

    // Tried making a function that finds an event by its ID, but javascript sucks too much!
    //const selectedEvent = findEventByID(selectedEventNumber, filteredEvents);

    // Find the events chosen by the user (using its id)
    let selectedEvent = {};
    filteredEvents.forEach(sportObj => {
        sportObj.events.forEach(event => {
            if (event.id === selectedEventNumber) {
                return selectedEvent = event;
            }
        });
    });

    // Ask user to choose an odd/team to bet on
    const { selectedTeam } = await feedView.showSelectTeamOddScreen(selectedEvent)
    let team = '';
    let odd = 0;

    if (selectedTeam === 'TEAM_1') {
        team = selectedEvent.teamA;
        odd = selectedEvent.bets._1x2.odd1;
    }
    else if (selectedTeam === 'TEAM_2') {
        team = selectedEvent.teamB;
        odd = selectedEvent.bets._1x2.odd2;
    }
    else {
        team = 'tie';
        odd = selectedEvent.bets._1x2.oddX;
    }

    // Ask user to select a coin
    const { selectedCoin } = await feedView.showSelectCoinScreen();

    // Ask user to enter an ammount to bet
    const { enteredAmount } = await feedView.showBetAmmountScreen();

 
    */

    // Save bet to betting slip
    /*
    const bet = {
        eventID: selectedEventNumber,
        date: selectedEvent.date,
        time: selectedEvent.startTime,
        type: '1x2',
        bettedTeam: team,
        bettedOdd: odd,
        coin: selectedCoin,
        amount: enteredAmount
    }*/

    const bet = {
        eventID: "eventID42",
        date: "2021-01-14",
        time: "19:00",
        type: '1x2',
        bettedTeam: "FC Porto",
        bettedOdd: "1.23",
        coin: "BTC",
        amount: "15.40"
    }

    bettingSlipController.addToBettingSlip(bet);

    await acknowledgePrompts.promptForAcknowledge("🙌 Bet Submitted to betting slip 🙌", "The submitted bet can be seen in your betting slip");

}

async function bettingSlipScreen() {
    await bettingSlipController.bettingSlipScreen()
}

async function filterScreen() {


    const { filterType, filterSearch } = await feedView.showFilterScreen();

    if (filterType === 'TEAM')
        currentFilter.team = filterSearch;
    else if (filterType === 'DATE')
        currentFilter.date = filterSearch;
    else if (filterType === 'SPORT')
        currentFilter.sport = filterSearch;

    const filteredBySport = Array.from(filteredEvents.filter((sportObj) => sportObj.sport.indexOf(currentFilter.sport) !== -1));



    let filteredByDate = []
    filteredBySport.map((sportObj) => {
        filteredByDate.push(
            {
                sport: sportObj.sport,
                events: sportObj.events.filter((evt) => evt.date.indexOf(currentFilter.date) !== -1)
            }
        );
    })

    // bet.date.indexOf(currentFilter.date) !== -1

    let filteredByTeam = []
    filteredByDate.map((sportObj) => {
        filteredByTeam.push(
            {
                sport: sportObj.sport,
                events: sportObj.events.filter(
                    (evt) =>
                        evt.teamA.toLowerCase().indexOf(currentFilter.team.toLowerCase()) !== -1
                        || evt.teamB.toLowerCase().indexOf(currentFilter.team.toLowerCase()) !== -1)
            }
        )
    });

    filteredEvents = Array.from(filteredByTeam)

}

