<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Author>zhao</Author>
  <LastAuthor>zhao</LastAuthor>
  <Created>2015-06-14T08:31:09Z</Created>
  <LastSaved>2015-06-14T08:38:02Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <CustomDocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <KSOProductBuildVer dt:dt="string">2052-9.1.0.5060</KSOProductBuildVer>
 </CustomDocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8520</WindowHeight>
  <WindowWidth>19830</WindowWidth>
  <WindowTopX>0</WindowTopX>
  <WindowTopY>0</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Center"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="m356087924">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="Arial Unicode MS" x:CharSet="134" x:Family="Swiss"
    ss:Color="#000000"/>
   <Interior ss:Color="#33CCCC" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m356087944">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="Arial Unicode MS" x:CharSet="134" x:Family="Swiss"
    ss:Color="#000000"/>
   <Interior ss:Color="#33CCCC" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s21">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="Arial Unicode MS" x:CharSet="134" x:Family="Swiss"
    ss:Color="#000000"/>
   <Interior ss:Color="#FFFFCC" ss:Pattern="Solid"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="SOID识别码">
  <Table ss:ExpandedColumnCount="11" ss:ExpandedRowCount="3" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
   <Row ss:Height="15">
    <Cell ss:MergeDown="1" ss:StyleID="s21"><Data ss:Type="String">区域</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="s21"><Data ss:Type="String">代理商名称</Data></Cell>
    <Cell ss:MergeAcross="1" ss:StyleID="s21"><Data ss:Type="String">CPP</Data></Cell>
    <Cell ss:MergeAcross="1" ss:StyleID="s21"><Data ss:Type="String">WFP</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="s21"><Data ss:Type="String">iPF</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="s21"><Data ss:Type="String">PP</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="m356087924"><Data ss:Type="String">NEW SOID</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="m356087944"><Data ss:Type="String">New Verification Code</Data></Cell>
    <Cell ss:MergeDown="1" ss:StyleID="s21"><Data ss:Type="String">备注</Data></Cell>
   </Row>
   <Row ss:Height="15">
    <Cell ss:Index="3" ss:StyleID="s21"><Data ss:Type="String">DP</Data></Cell>
    <Cell ss:StyleID="s21"><Data ss:Type="String">PGA</Data></Cell>
    <Cell ss:StyleID="s21"><Data ss:Type="String">TDS</Data></Cell>
    <Cell ss:StyleID="s21"><Data ss:Type="String">DGS</Data></Cell>
   </Row>
<#list dataList as data>
   <Row>
    <Cell><Data ss:Type="String">${data.saleRegion}</Data></Cell>
    <Cell><Data ss:Type="String">${data.shopName}</Data></Cell>
    <Cell><Data ss:Type="String">${data.dp}</Data></Cell>
    <Cell><Data ss:Type="String">${data.pga}</Data></Cell>
    <Cell><Data ss:Type="String">${data.tds}</Data></Cell>
    <Cell><Data ss:Type="String">${data.dgs}</Data></Cell>
    <Cell><Data ss:Type="String">${data.ipf}</Data></Cell>
    <Cell><Data ss:Type="String">${data.pp}</Data></Cell>
    <Cell><Data ss:Type="String">${data.newSoid}</Data></Cell>
    <Cell><Data ss:Type="String">${data.newVerificationCode}</Data></Cell>
    <Cell><Data ss:Type="String">${data.note}</Data></Cell>
   </Row>
</#list>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.51180555555555596"/>
    <Footer x:Margin="0.51180555555555596"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>2</ActiveRow>
     <ActiveCol>10</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet2">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.51180555555555596"/>
    <Footer x:Margin="0.51180555555555596"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet3">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.51180555555555596"/>
    <Footer x:Margin="0.51180555555555596"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
