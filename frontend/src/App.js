import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';
import Login from './components/Login';
import ChartComponent from './components/Chart';

function App() {
  // form
  const [formData, setFormData] = useState({
    playerName: '', position: '', number: '', opponent: '', gameDate: '',
    twoPScored: '', twoPShot: '', threePScored: '', threePShot: '', ftScored: '', ftShot: '',
  });
  //Data lists and control
  const [playerStatsList, setPlayerStatsList] = useState([]);
  const [editing,setEditing] = useState(false);
  const [currentIds, setCurrentIds] = useState({ playerId: null, gameId: null, statsId: null });
  const [view, setView] =useState('history');
  const [loggedIn,setLoggedIn] = useState(false);
  const [sortKey, setSortKey]= useState(null);

  // fetch data from backend
  useEffect(() => { fetchData(); },[]);
  const fetchData = () => {
    axios.get('http://localhost:8080/playerstats')
      .then(res =>setPlayerStatsList(res.data))
      .catch(err =>console.error(err));
  };
  // Handle form inputs
  const handleChange =(e) => {
    const { name,value } = e.target;
    setFormData({...formData,[name]: value });
  };
  //Submit form create/update
  const handleSubmit = (e) => {
    e.preventDefault();
    if (parseInt(formData.twoPScored) >parseInt(formData.twoPShot) ||
        parseInt(formData.threePScored) > parseInt(formData.threePShot) ||
        parseInt(formData.ftScored) >parseInt(formData.ftShot)) {
      alert("Scored cannot be more than Shot");
      return;
    }

    if (parseInt(formData.number)<0 ||parseInt(formData.number) > 99) {
      alert("Number must be between 0-99");
      return;
    }

    const payload ={
      playerName: formData.playerName,
      position: formData.position,
      number:formData.number ?parseInt(formData.number) :null,
      opponent: formData.opponent,
      gameDate: formData.gameDate,
      twoPScored: parseInt(formData.twoPScored),
      twoPShot: parseInt(formData.twoPShot),
      threePScored: parseInt(formData.threePScored),
      threePShot: parseInt(formData.threePShot),
      ftScored:parseInt(formData.ftScored),
      ftShot: parseInt(formData.ftShot)
    };
    if (editing){
      axios.put('http://localhost:8080/combined/update', {
        ...payload,
        playerId: currentIds.playerId,
        gameId:currentIds.gameId,
        statsId: currentIds.statsId
      })
      .then(()=> { fetchData();resetForm(); })
      .catch(err =>console.error(err));
    } else {
      axios.post('http://localhost:8080/combined/create',payload)
      .then(() => { fetchData(); resetForm(); })
      .catch(err => console.error(err));
    }
  };

  //reset form after submit or cancel
  const resetForm = ()=> {
    setFormData({
      playerName: '', position: '',number: '', opponent: '', gameDate: '',
      twoPScored: '', twoPShot: '', threePScored: '', threePShot: '',ftScored: '', ftShot: ''
    });
    setEditing(false);
    setCurrentIds({ playerId: null, gameId: null, statsId: null });
  };

  // edit
  const handleEdit =(ps) => {
    if (!ps.player?.id||!ps.game?.id ||!ps.id) return;
    setFormData({
      playerName:ps.player?.name ||'',
      position: ps.player?.position || '',
      number: ps.player?.number || '',
      opponent: ps.game?.opponent || '',
      gameDate: ps.game?.date || '',
      twoPScored: ps.twoPScored, twoPShot: ps.twoPShot,
      threePScored: ps.threePScored, threePShot: ps.threePShot,
      ftScored: ps.ftScored, ftShot: ps.ftShot
    });
    setEditing(true);
    setCurrentIds({ playerId:ps.player.id, gameId: ps.game.id, statsId: ps.id });
    setView('new');
  };
  // Delete combined entry
  const handleDelete = (ps) => {
    if (!ps.player?.id ||!ps.game?.id || !ps.id) return;
    axios.delete(`http://localhost:8080/combined/delete`, {
      params: { playerId: ps.player.id, gameId: ps.game.id, statsId: ps.id }
    })
    .then(() => fetchData())
    .catch(err=> console.error(err));
  };

  // Compute averages array
  const averages =[];
  playerStatsList.forEach(ps => {
    let found = averages.find(a => a.playerName ===ps.player.name);
    if (!found) {
      found = {
        playerName: ps.player.name,
        position: ps.player.position,
        number: ps.player.number,
        twoPScored: 0, twoPShot: 0,
        threePScored: 0, threePShot: 0,
        ftScored: 0, ftShot: 0,
        games: 0
      };
      averages.push(found);
    }
    found.twoPScored += ps.twoPScored;
    found.twoPShot += ps.twoPShot;
    found.threePScored += ps.threePScored;
    found.threePShot += ps.threePShot;
    found.ftScored += ps.ftScored;
    found.ftShot += ps.ftShot;
    found.games += 1;
  });

  // Sort averages by key
  const sortedAverages =[...averages].sort((a, b) => {
    if (!sortKey) return 0;
    let aValue = 0,bValue = 0;
    if (sortKey ==='2P%') {
      aValue = a.twoPShot ? (a.twoPScored / a.twoPShot) : 0;
      bValue = b.twoPShot ? (b.twoPScored/ b.twoPShot) : 0;
    } else if (sortKey==='3P%') {
      aValue = a.threePShot ? (a.threePScored/ a.threePShot): 0;
      bValue = b.threePShot ? (b.threePScored / b.threePShot):0;
    } else if (sortKey === 'FT%') {
      aValue = a.ftShot ? (a.ftScored /a.ftShot) : 0;
      bValue = b.ftShot ? (b.ftScored / b.ftShot) :0;
    } else if (sortKey=== 'Avg Points') {
      const aPoints = (a.twoPScored*2)+(a.threePScored*3)+a.ftScored;
      const bPoints = (b.twoPScored*2)+(b.threePScored*3)+b.ftScored;
      aValue = a.games ?aPoints/a.games : 0;
      bValue = b.games? bPoints/b.games: 0;
    }
    return bValue -aValue;
  });

  if (!loggedIn) return <Login onLogin={()=> setLoggedIn(true)} />;

  return (
    <div className="App">
      <video autoPlay loop muted playsInline className="background-video">
        <source src="/assets/background.mp4" type="video/mp4" />
      </video>
      <div className="center-panel">
        <div style={{ marginBottom:'20px' }}>
          <button onClick={()=>setView('new')}>New Stats</button>
          <button onClick={() => setView('history')}>Match History</button>
          <button onClick={()=> setView('avg')}>Average Stats</button>
          <button onClick={() => setView('charts')}>Charts</button>
          <button style={{ float: 'right' }} onClick={() => setLoggedIn(false)}>Logout</button>
        </div>
        {view === 'new' && (
          <form onSubmit={handleSubmit} style={{display: 'flex', flexWrap: 'wrap', gap: '10px', marginBottom: '30px' }}>
            <input name="playerName" placeholder="Name" value={formData.playerName} onChange={handleChange} required />
            <select name="position" value={formData.position} onChange={handleChange} required
              style={{ background:'rgba(255,255,255,0.1)', color: 'white', borderRadius: '5px', padding: '8px' }}>
              <option value="">Select Position</option>
              <option value="PG">PG</option>
              <option value="SG">SG</option>
              <option value="SF">SF</option>
              <option value="PF">PF</option>
              <option value="C">C</option>
            </select>
            <input type="number" name="number" placeholder="Number (0-99)" value={formData.number} onChange={handleChange} />
            <input name="opponent" placeholder="Opponent" value={formData.opponent} onChange={handleChange} />
            <input type="date" name="gameDate" value={formData.gameDate} onChange={handleChange} required />
            <input type="number" name="twoPScored" placeholder="2P Scored" value={formData.twoPScored} onChange={handleChange} required />
            <input type="number" name="twoPShot" placeholder="2P Shot" value={formData.twoPShot} onChange={handleChange} required />
            <input type="number" name="threePScored" placeholder="3P Scored" value={formData.threePScored} onChange={handleChange} required />
            <input type="number" name="threePShot" placeholder="3P Shot" value={formData.threePShot} onChange={handleChange} required />
            <input type="number" name="ftScored" placeholder="FT Scored" value={formData.ftScored} onChange={handleChange} required />
            <input type="number" name="ftShot" placeholder="FT Shot" value={formData.ftShot} onChange={handleChange} required />
            <button type="submit">{editing ? "Update" : "Submit"}</button>
            {editing && <button type="button" onClick={resetForm}>Cancel</button>}
          </form>
        )}

        {view ==='history'&& (
          <table>
            <thead>
              <tr><th>Player</th><th>Pos</th><th>#</th><th>Opponent</th><th>Date</th><th>2P</th><th>3P</th><th>FT</th><th></th></tr>
            </thead>
            <tbody>
              {playerStatsList.map(ps => (
                <tr key={ps.id}>
                  <td>{ps.player?.name}</td>
                  <td>{ps.player?.position}</td>
                  <td>{ps.player?.number}</td>
                  <td>{ps.game?.opponent}</td>
                  <td>{ps.game?.date}</td>
                  <td>{ps.twoPScored}/{ps.twoPShot}</td>
                  <td>{ps.threePScored}/{ps.threePShot}</td>
                  <td>{ps.ftScored}/{ps.ftShot}</td>
                  <td>
                    <button onClick={()=> handleEdit(ps)}>✏️</button>
                    <button onClick={() =>handleDelete(ps)}>🗑</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        {view === 'avg' && (
          <table>
            <thead>
              <tr>
                <th>Name</th><th>Pos</th><th>#</th>
                <th onClick={() =>setSortKey('2P%')}>2P%</th>
                <th onClick={() => setSortKey('3P%')}>3P%</th>
                <th onClick={() => setSortKey('FT%')}>FT%</th>
                <th onClick={() => setSortKey('Avg Points')}>Avg Points</th>
              </tr>
            </thead>
            <tbody>
              {sortedAverages.map(avg => {
                const points =(avg.twoPScored*2) +(avg.threePScored*3) +avg.ftScored;
                return (
                  <tr key={avg.playerName}>
                    <td>{avg.playerName}</td>
                    <td>{avg.position}</td>
                    <td>{avg.number}</td>
                    <td>{avg.twoPShot ? ((avg.twoPScored / avg.twoPShot)*100).toFixed(1)+"%" : "0%"}</td>
                    <td>{avg.threePShot ? ((avg.threePScored / avg.threePShot)*100).toFixed(1)+"%" : "0%"}</td>
                    <td>{avg.ftShot ? ((avg.ftScored / avg.ftShot)*100).toFixed(1)+"%" : "0%"}</td>
                    <td>{(points/avg.games).toFixed(1)}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
        {view ==='charts' && (
          <ChartComponent averages={averages} darkMode />
        )}
      </div>
    </div>
  );
}
export default App;
