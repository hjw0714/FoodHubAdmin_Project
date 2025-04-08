import { useState } from 'react';
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
import '../assets/css/postReport.css'; // 스타일 공통 재사용

const categoryData = {
    1: {
        name: '외식업정보게시판',
        year: [
            { name: '2023', count: 120 },
            { name: '2024', count: 180 },
            { name: '2025', count: 210 },
        ],
        month: [
            { name: '1월', count: 10 },
            { name: '2월', count: 15 },
            { name: '3월', count: 18 },
            { name: '4월', count: 22 },
            { name: '5월', count: 20 },
            { name: '6월', count: 25 },
            { name: '7월', count: 30 },
            { name: '8월', count: 28 },
            { name: '9월', count: 24 },
            { name: '10월', count: 20 },
            { name: '11월', count: 15 },
            { name: '12월', count: 18 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 10) + 1 })),
    },
    2: {
        name: '자유게시판',
        year: [
            { name: '2023', count: 300 },
            { name: '2024', count: 280 },
            { name: '2025', count: 320 },
        ],
        month: [
            { name: '1월', count: 25 }, { name: '2월', count: 28 }, { name: '3월', count: 30 }, { name: '4월', count: 26 },
            { name: '5월', count: 22 }, { name: '6월', count: 24 }, { name: '7월', count: 29 }, { name: '8월', count: 27 },
            { name: '9월', count: 25 }, { name: '10월', count: 21 }, { name: '11월', count: 20 }, { name: '12월', count: 23 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 15) + 5 })),
    },
    3: {
        name: '알바공고게시판',
        year: [
            { name: '2023', count: 80 },
            { name: '2024', count: 90 },
            { name: '2025', count: 100 },
        ],
        month: [
            { name: '1월', count: 5 }, { name: '2월', count: 8 }, { name: '3월', count: 10 }, { name: '4월', count: 9 },
            { name: '5월', count: 8 }, { name: '6월', count: 10 }, { name: '7월', count: 11 }, { name: '8월', count: 12 },
            { name: '9월', count: 9 }, { name: '10월', count: 7 }, { name: '11월', count: 6 }, { name: '12월', count: 5 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 5) + 1 })),
    },
    4: {
        name: '질문게시판',
        year: [
            { name: '2023', count: 200 },
            { name: '2024', count: 220 },
            { name: '2025', count: 240 },
        ],
        month: [
            { name: '1월', count: 15 }, { name: '2월', count: 18 }, { name: '3월', count: 22 }, { name: '4월', count: 20 },
            { name: '5월', count: 18 }, { name: '6월', count: 25 }, { name: '7월', count: 27 }, { name: '8월', count: 23 },
            { name: '9월', count: 20 }, { name: '10월', count: 18 }, { name: '11월', count: 17 }, { name: '12월', count: 16 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 10) + 2 })),
    },
    5: {
        name: '중고장비거래게시판',
        year: [
            { name: '2023', count: 50 },
            { name: '2024', count: 55 },
            { name: '2025', count: 65 },
        ],
        month: [
            { name: '1월', count: 4 }, { name: '2월', count: 5 }, { name: '3월', count: 6 }, { name: '4월', count: 5 },
            { name: '5월', count: 6 }, { name: '6월', count: 7 }, { name: '7월', count: 8 }, { name: '8월', count: 7 },
            { name: '9월', count: 5 }, { name: '10월', count: 6 }, { name: '11월', count: 4 }, { name: '12월', count: 5 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 3) + 1 })),
    },
    6: {
        name: '매장홍보게시판',
        year: [
            { name: '2023', count: 160 },
            { name: '2024', count: 170 },
            { name: '2025', count: 180 },
        ],
        month: [
            { name: '1월', count: 12 }, { name: '2월', count: 14 }, { name: '3월', count: 16 }, { name: '4월', count: 15 },
            { name: '5월', count: 13 }, { name: '6월', count: 17 }, { name: '7월', count: 18 }, { name: '8월', count: 16 },
            { name: '9월', count: 14 }, { name: '10월', count: 13 }, { name: '11월', count: 12 }, { name: '12월', count: 13 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 7) + 2 })),
    },
    7: {
        name: '협력업체게시판',
        year: [
            { name: '2023', count: 70 },
            { name: '2024', count: 80 },
            { name: '2025', count: 90 },
        ],
        month: [
            { name: '1월', count: 6 }, { name: '2월', count: 8 }, { name: '3월', count: 9 }, { name: '4월', count: 10 },
            { name: '5월', count: 9 }, { name: '6월', count: 11 }, { name: '7월', count: 12 }, { name: '8월', count: 10 },
            { name: '9월', count: 8 }, { name: '10월', count: 9 }, { name: '11월', count: 7 }, { name: '12월', count: 8 },
        ],
        day: Array.from({ length: 31 }, (_, i) => ({ name: `03-${i + 1}`, count: Math.floor(Math.random() * 4) + 1 })),
    }
};


const CategoryPostList = () => {
    const [selectedCategory, setSelectedCategory] = useState('1');

    const category = categoryData[selectedCategory];

    return (
        <div className="dashboard-section">
            <h3>📊 카테고리별 게시글 수</h3>
            <p>년도, 월, 일 기준으로 카테고리별 게시글 수 통계를 확인할 수 있습니다.</p>

            <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)} style={{ margin: '20px 0' }}>
                {Object.entries(categoryData).map(([id, cat]) => (
                    <option key={id} value={id}>
                        {cat.name}
                    </option>
                ))}
            </select>

            <h4>📅 연도별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.year}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#8884d8" />
                </BarChart>
            </ResponsiveContainer>

            <h4>📆 월별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.month}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#82ca9d" />
                </BarChart>
            </ResponsiveContainer>

            <h4>🗓️ 일별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={category.day}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" interval={2} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="count" fill="#ffc658" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

export default CategoryPostList;
