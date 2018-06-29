import React, { Component } from 'react';
import axios from 'axios';
import ServiceInfoRow from './ServiceInfoRow';
import { Col, Grid, Row, Table } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';


class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      ready: false,
      services: []
    }
  }

  componentDidMount() {
    setInterval(this.fetchData, 2000);
  }

  fetchData = () => {
    axios.get('/api/list')
      .then(({data}) => this.setState({ready: true, services: data}))
      .catch(e => console.error(e));
  };

  render() {
    const rows = this.state.services.map(service =>
      <ServiceInfoRow key={service.name} service={service}/>
    );

    const table = (
      <Table bordered>
        <thead>
        <tr>
          <th>Name</th>
          <th>Description</th>
          <th>Status</th>
        </tr>
        </thead>
        <tbody>
        {rows}
        </tbody>
      </Table>
    );

    const loading = <h1 className="text-center" style={{fontSize: '20pt'}}>Loading ...</h1>;

    const view = this.state.ready ? table : loading;

    return (
      <Grid>
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
