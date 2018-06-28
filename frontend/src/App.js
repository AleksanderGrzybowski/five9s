import React, { Component } from 'react';
import axios from 'axios';
import ServiceInfoRow from './ServiceInfoRow';

class App extends Component {
 
  constructor(props) {
    super(props);

    this.state = {
      services: []
    }
  }

  componentDidMount() {
    setInterval(this.fetchData, 2000);
  }

  fetchData = () => {
    axios.get('/api/list')
      .then(({data}) => this.setState({services: data}))
      .catch(e => console.error(e));
  };

  render() {
    const services = this.state.services.map(service =>
      <ServiceInfoRow key={service.name} service={service}/>
    );

    return (
      <div className="App">
        <h1>Services:</h1>
        <ul>{services}</ul>
      </div>
    );
  }
}

export default App;
