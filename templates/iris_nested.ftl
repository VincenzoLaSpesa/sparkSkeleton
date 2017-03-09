	<div class="row">
      <table class="table table-condensed table-striped" id="dataset">
        <thead>
          <tr>
            <th>Species</th>
            <th>Sepal Length</th>
            <th>Sepal Width</th>
            <th>Petal Length</th>
            <th>Petal Width</th>
          </tr>
        </thead>
                <tbody>
                    <#list dataset as item>
                    <tr style="font-style: oblique;font-weight: bold;">
                        <td><strong>${item.species}</strong></td>
                        <td>${item.sepalLength}</td>
                        <td>${item.sepalWidth}</td>
                        <td>${item.petalLength}</td>
                        <td>${item.petalWidth}</td>
                        </tr>
                    </#list>
                    </tbody>
      </table>
    </div>