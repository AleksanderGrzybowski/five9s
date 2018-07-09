import React, { Component } from 'react';
import axios from 'axios';
import { Col, Grid, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import ServiceTable from './ServiceTable';
import LoadingText from './LoadingText';

const refreshInterval = 30000;
const endpoint = '/api/list';

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      isReady: false,
      services: []
    }
  }

  componentDidMount() {
    setInterval(this.fetchData, refreshInterval);
    this.fetchData();
  }

  fetchData = () => {
    axios.get(endpoint)
      .then(({data}) => this.setState({isReady: true, services: data}))
      .catch(e => {
        this.setState({isReady: false});
        console.error(e);
      });
  };

  render() {
    const view = this.state.isReady ? <ServiceTable services={this.state.services}/> : <LoadingText/>;

    return (
      <Grid fluid>
        <Row>
          <Col md={12}>
            {view}
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default App;
