import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';

// core components

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public datasets: any;
  public data: any;
  public salesChart?: any;
  public clicked: boolean = true;
  public clicked1: boolean = false;

  ngOnInit() {
    this.datasets = [
      [0, 20, 10, 30, 15, 40, 20, 60, 60],
      [0, 20, 5, 25, 10, 30, 15, 40, 40],
    ];
    this.data = this.datasets[0];

    this.chartJsFunction();
  }

  chartJsFunction(): void {
    var chartSales = <HTMLCanvasElement>document.getElementById('chart-sales-dark');

    // Init chart
    var salesChart = new Chart(chartSales, {
      type: 'bar',
      data: {
        labels: ['Très bien', 'Bien', 'Assez bien', 'Passable'],
        datasets: [
          {
            label: 'Mention Soutenance',
            data: [23, 23, 16, 32],
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });

    var chartOrders = <HTMLCanvasElement>document.getElementById('chart-orders');
    var salesChart = new Chart(chartOrders, {
      type: 'line',
      data: {
        labels: ['Très bien', 'Bien', 'Assez bien', 'Passable'],
        datasets: [
          {
            label: 'Mention Soutenance',
            data: [23, 23, 16, 32],
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });
  }

  public updateOptions() {
    if (this.salesChart?.data.datasets) {
      this.salesChart.data.datasets[0].data = this.data;
      this.salesChart?.update();
    }
  }

  startChartJs(): void {}
}
