import React from 'react';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function ChartComponent({averages}) {
  const data = {
    labels: averages.map(a =>a.playerName),
    datasets: [
      {
        label: 'Avg Points',
        data: averages.map(a =>{
          const points = (a.twoPScored*2)+(a.threePScored*3)+a.ftScored;
          return (points/ a.games).toFixed(1);
        }),
        backgroundColor:'rgba(97, 218, 251, 0.8)',
        borderColor:'#61dafb',
        borderWidth: 1
      },
      {
        label: '2P%',
        data: averages.map(a => a.twoPShot ? ((a.twoPScored/a.twoPShot)*100).toFixed(1) :0),
        backgroundColor: 'rgba(150, 150, 255, 0.8)',
        borderColor: '#9999ff',
        borderWidth: 1
      },
      {
        label:'3P%',
        data: averages.map(a => a.threePShot ? ((a.threePScored/a.threePShot)*100).toFixed(1) : 0),
        backgroundColor: 'rgba(255, 180, 70, 0.8)',
        borderColor: '#ffb446',
        borderWidth:1
      },
      {
        label:  'FT%' ,
        data: averages.map(a => a.ftShot ? ((a.ftScored/a.ftShot)*100).toFixed(1) : 0),
        backgroundColor: 'rgba(255, 100, 100, 0.8)',
        borderColor: '#ff6464',
        borderWidth: 1
      }
    ]
  };
  const options= {
    responsive:true,
    maintainAspectRatio: false,
    plugins: {
      legend: { labels:{ color: 'white' }},
      title: {
        display: true,
        text: 'Player Average Stats',
        color: 'white',
        font: { size:20 }
      }
    },
    scales: {
      x: {
        ticks: { color:'white' },
        grid: { color: 'rgba(255,255,255,0.1)' }
      },
      y: {
        ticks: { color: 'white' },
        grid: { color: 'rgba(255,255,255,0.1)' }
      }
    }
  };
  return(
    <div style={{ height:'400px', marginTop: '30px' }}>
      <Bar data={data} options={options} />
    </div>
  );
}
export default ChartComponent;
